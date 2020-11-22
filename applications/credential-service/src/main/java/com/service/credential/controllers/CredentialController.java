package com.service.credential.controllers;

import com.service.credential.controllers.request.CredentialCreationRequest;
import com.service.credential.controllers.response.CredentialResponseWithouPassword;
import com.service.credential.services.CreateCredentialService;
import com.service.credential.services.GetCredentialPasswordService;
import com.service.credential.services.GetCredentialsWithoutPasswordService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CreateCredentialService createCredentialService;

    @Autowired
    private GetCredentialsWithoutPasswordService getCredentialsWithoutPasswordService;

    @Autowired
    private GetCredentialPasswordService getCredentialPasswordService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("creation")
    public void createCredential(@RequestBody @Validated CredentialCreationRequest credentialCreationRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        createCredentialService.createCredential(credentialCreationRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner-credentials")
    public List<CredentialResponseWithouPassword> getOwnerCredential(@RequestParam("owner_id") Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {
        return getCredentialsWithoutPasswordService.getCredentials(ownerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("credential-auth")
    public String getCredentialPassword(
            @RequestParam("owner_id") Long ownerId,
            @RequestParam("credential_id") Long credentialId
    )
            throws ProposalException, IOException, InvalidArgumentException {
        return getCredentialPasswordService.getPassword(ownerId, credentialId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("credential-remove")
    public String removeCredential(
            @RequestParam("owner_id") Long ownerId,
            @RequestParam("credential_id") Long credentialId
    )
            throws ProposalException, IOException, InvalidArgumentException {
        return getCredentialPasswordService.getPassword(ownerId, credentialId);
    }

}
