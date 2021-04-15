package com.service.credential.services;

import com.service.common.crypto.SymmetricCrypto;
import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.Owner;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.common.domain.fabric.credential.CredentialRecordModel;
import com.service.common.repository.OwnerRepository;
import com.service.common.service.account.GetAccountAssetByIdCommonService;
import com.service.common.service.fabric.credential.GetCredentialAssetsByOwnerIdService;
import com.service.common.service.fabric.credential.SaveCredentialAssetService;
import com.service.credential.controllers.request.CredentialCreationRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCredentialService {

    @Autowired
    private GetCredentialAssetsByOwnerIdService getCredentialAssetsByOwnerIdService;

    @Autowired
    private SaveCredentialAssetService saveCredentialAssetService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private GetAccountAssetByIdCommonService getAccountAssetByIdCommonService;

    public void createCredential(
            CredentialCreationRequest credentialCreationRequest, boolean isActive, String credentialKeyString
    ) throws ProposalException, IOException, InvalidArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        Long createdAt = zonedDateTime.toInstant().toEpochMilli();

        List<CredentialAsset> credentialAssets =
                getCredentialAssetsByOwnerIdService.getCredentialsByOwnerId(credentialCreationRequest.getOwnerId());

        Owner owner = ownerRepository.findById(credentialCreationRequest.getOwnerId()).get();
        AccountAsset ownerAsset = getAccountAssetByIdCommonService.getAccount(owner.getId());

        List<AccountAsset> accountAssets = new ArrayList<>();
        accountAssets.add(ownerAsset);

        credentialCreationRequest.getHeirsIds().forEach(heirId -> {
            AccountAsset accountAsset = getAccountAssetByIdCommonService.getAccount(heirId);
            accountAssets.add(accountAsset);
        });

        SecretKey updateSymmetricKey =
                credentialKeyString != null ? SymmetricKeyCrypto.decryptKey(credentialKeyString, ownerAsset) : null;

        SecretKey credentialKey =
                updateSymmetricKey != null ? updateSymmetricKey : SymmetricCrypto.generateKey("password");

        String encryptedSymmetricKey = SymmetricKeyCrypto.encryptKey(credentialKey.getEncoded(), accountAssets);

        CredentialRecordModel credentialRecordModel = new CredentialRecordModel(
                credentialCreationRequest.getCredentialId() != null
                        ? credentialCreationRequest.getCredentialId()
                        : credentialAssets.size() + 1,
                SymmetricCrypto.encrypt(credentialCreationRequest.getName(), credentialKey),
                SymmetricCrypto.encrypt(credentialCreationRequest.getDescription(), credentialKey),
                SymmetricCrypto.encrypt(credentialCreationRequest.getLink(), credentialKey),
                SymmetricCrypto.encrypt(credentialCreationRequest.getLogin(), credentialKey),
                SymmetricCrypto.encrypt(credentialCreationRequest.getPassword(), credentialKey),
                credentialCreationRequest.getOwnerId(),
                SymmetricCrypto.encrypt(credentialCreationRequest.getHeirsIds().toString(), credentialKey),
                isActive,
                createdAt,
                encryptedSymmetricKey
        );

        saveCredentialAssetService.createTransaction(credentialRecordModel);
    }

}
