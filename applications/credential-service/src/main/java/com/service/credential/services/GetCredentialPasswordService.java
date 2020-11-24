package com.service.credential.services;

import com.service.credential.controllers.response.CredentialResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCredentialPasswordService {

    @Autowired
    private GetOwnerCredentialsService getOwnerCredentialsService;

    public String getPassword(Long ownerId, Long credentialId) throws ProposalException, IOException, InvalidArgumentException {
        List<CredentialResponse> credentialResponses = getOwnerCredentialsService.getOwnerCredentials(ownerId);

        CredentialResponse credentialResponse = credentialResponses.stream().filter(credential ->
             credential.getCredentialId() == credentialId
        ).collect(Collectors.toList()).get(0);

        return credentialResponse.getPassword();
    }

}
