package com.service.credential.services;

import com.service.credential.controllers.request.CredentialCreationRequest;
import com.service.credential.controllers.request.CredentialRemoveRequest;
import com.service.credential.controllers.response.CredentialResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RemoveCredentialService {

    @Autowired
    private CreateCredentialService createCredentialService;

    @Autowired
    private GetCredentialByIdService getCredentialByIdService;

    public void removeCredential(CredentialRemoveRequest credentialRemoveRequest)
            throws ProposalException, IOException, InvalidArgumentException {

        CredentialResponse selectedCredential = getCredentialByIdService
                .getCredential(credentialRemoveRequest.getOwnerId(), credentialRemoveRequest.getCredentialId());

        CredentialCreationRequest credentialCreationRequest = new CredentialCreationRequest(
                selectedCredential.getCredentialId(),
                selectedCredential.getName(),
                selectedCredential.getLogin(),
                selectedCredential.getPassword(),
                selectedCredential.getLink(),
                selectedCredential.getDescription(),
                selectedCredential.getHeirsIds(),
                selectedCredential.getCredentialOwnerId()
        );

        createCredentialService.createCredential(credentialCreationRequest, false);
    }

}
