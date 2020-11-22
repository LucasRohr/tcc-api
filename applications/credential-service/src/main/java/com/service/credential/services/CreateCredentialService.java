package com.service.credential.services;

import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.common.domain.fabric.credential.CredentialRecordModel;
import com.service.common.service.fabric.credential.GetCredentialAssetsByOwnerIdService;
import com.service.common.service.fabric.credential.SaveCredentialAssetService;
import com.service.credential.controllers.request.CredentialCreationRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CreateCredentialService {

    @Autowired
    private GetCredentialAssetsByOwnerIdService getCredentialAssetsByOwnerIdService;

    @Autowired
    private SaveCredentialAssetService saveCredentialAssetService;

    public void createCredential(CredentialCreationRequest credentialCreationRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        Long createdAt = zonedDateTime.toInstant().toEpochMilli();

        List<CredentialAsset> credentialAssets =
                getCredentialAssetsByOwnerIdService.getCredentialsByOwnerId(credentialCreationRequest.getOwnerId());

        CredentialRecordModel credentialRecordModel = new CredentialRecordModel(
                credentialCreationRequest.getCredentialId() != null
                        ? credentialCreationRequest.getCredentialId()
                        : credentialAssets.size() + 1,
                credentialCreationRequest.getName(),
                credentialCreationRequest.getDescription(),
                credentialCreationRequest.getLink(),
                credentialCreationRequest.getLogin(),
                credentialCreationRequest.getPassword(),
                credentialCreationRequest.getOwnerId(),
                credentialCreationRequest.getHeirsIds().toString(),
                true,
                createdAt
        );

        System.out.println("credentialRecordModel ARRAYYYY "  + credentialCreationRequest.getHeirsIds().toString());

        saveCredentialAssetService.createTransaction(credentialRecordModel);
    }

}
