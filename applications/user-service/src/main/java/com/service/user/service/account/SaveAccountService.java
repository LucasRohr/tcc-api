package com.service.user.service.account;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import com.service.common.crypto.AsymmetricCrypto;
import com.service.common.crypto.SymmetricCrypto;
import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.Account;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.account.AccountRecordModel;
import com.service.common.helpers.KeysConverter;
import com.service.common.repository.AccountRepository;
import com.service.common.service.account.GetAccountAssetByIdCommonService;
import com.service.common.service.fabric.account.SaveAccountAssetService;
import com.service.common.enums.AccountTypes;
import com.service.user.config.PasswordEncoder;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Service
public class SaveAccountService {
    private static final String keyArn = "arn:aws:kms:sa-east-1:665638235375:key/c252ed25-547c-49e0-99cd-816f44fa9726";

    private final AwsCrypto awsCrypto = AwsCrypto.builder()
            .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
            .build();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SaveOwnerService saveOwnerService;

    @Autowired
    private SaveHeirService saveHeirService;

    @Autowired
    private SaveAccountAssetService saveAccountAssetService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private GetAccountAssetByIdCommonService getAccountAssetByIdCommonService;

    public void saveAccount(Account account, AccountTypes accountType, Long ownerId, String cryptoPassword)
            throws ProposalException, InvalidArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        Account savedAccount = accountRepository.save(account);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());

        if(accountType == AccountTypes.OWNER) {
            saveOwnerService.saveOwner(savedAccount);
        } else {
            saveHeirService.saveHeir(savedAccount, ownerId);
        }

        KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn);
        Map<String, String> encryptionContext = Collections.singletonMap("masterKey", "masterValue");

        Long timestamp = zonedDateTime.toInstant().toEpochMilli();
        KeyPair accountKeys = AsymmetricCrypto.generateKeyPair(cryptoPassword);

        CryptoResult<byte[], KmsMasterKey> encryptedPrivateKeyResult =
                awsCrypto.encryptData(keyProvider, accountKeys.getPrivate().getEncoded(), encryptionContext);

        String encryptedPrivateKeyString = Base64.getEncoder().encodeToString(encryptedPrivateKeyResult.getResult());

        CryptoResult<byte[], KmsMasterKey> encryptedPublicKeyResult =
                awsCrypto.encryptData(keyProvider, accountKeys.getPublic().getEncoded(), encryptionContext);

        String encryptedPublicKeyString = Base64.getEncoder().encodeToString(encryptedPublicKeyResult.getResult());

        AccountRecordModel accountRecord = new AccountRecordModel(
                savedAccount.getId(),
                encryptedPrivateKeyString,
                encryptedPublicKeyString,
                accountType.toString(),
                encoder.bCryptPasswordEncoder().encode(cryptoPassword),
                timestamp
        );

        try {
            saveAccountAssetService.createTransaction(accountRecord);
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

}
