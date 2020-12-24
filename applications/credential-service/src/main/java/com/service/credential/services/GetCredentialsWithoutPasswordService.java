package com.service.credential.services;

import com.service.credential.controllers.response.CredentialResponse;
import com.service.common.dto.CredentialResponseWithouPassword;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetCredentialsWithoutPasswordService {

    @Autowired
    private GetOwnerCredentialsService getOwnerCredentialsService;

    public List<CredentialResponseWithouPassword> getCredentials(Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {

        List<CredentialResponse> credentialResponses = getOwnerCredentialsService.getOwnerCredentials(ownerId);
        List<CredentialResponseWithouPassword> credentialResponseWithouPasswords = new ArrayList<>();

        credentialResponses.forEach(credentialResponse -> {
            CredentialResponseWithouPassword credentialResponseWithouPassword = new CredentialResponseWithouPassword(
                    credentialResponse.getCredentialId(),
                    credentialResponse.getName(),
                    credentialResponse.getDescription(),
                    credentialResponse.getLink(),
                    credentialResponse.getLogin(),
                    credentialResponse.getCredentialOwnerId(),
                    credentialResponse.getHeirsIds(),
                    credentialResponse.getIsActive(),
                    credentialResponse.getCreatedAt()
            );

            credentialResponseWithouPasswords.add(credentialResponseWithouPassword);
        });

        return credentialResponseWithouPasswords;
    }

}
