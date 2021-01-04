package com.service.credential.services;

import com.service.credential.controllers.request.CredentialCreationRequest;
import com.service.common.dto.HeirsUpdateRequest;
import com.service.credential.controllers.response.CredentialResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UpdateCredentialHeirsService {

    @Autowired
    private GetCredentialByIdService getCredentialByIdService;

    @Autowired
    private CreateCredentialService createCredentialService;

    public void updateHeirs(HeirsUpdateRequest heirsUpdateRequest)
            throws ProposalException, IOException, InvalidArgumentException {

        CredentialResponse selectedCredential = getCredentialByIdService
                .getCredential(heirsUpdateRequest.getOwnerId(), heirsUpdateRequest.getCredentialId());

        CredentialCreationRequest credentialCreationRequest = new CredentialCreationRequest(
          selectedCredential.getCredentialId(),
                selectedCredential.getName(),
                selectedCredential.getLogin(),
                selectedCredential.getPassword(),
                selectedCredential.getLink(),
                selectedCredential.getDescription(),
                heirsUpdateRequest.getHeirsIds(),
                heirsUpdateRequest.getOwnerId()
        );

        createCredentialService.createCredential(credentialCreationRequest, true);
    }

}
