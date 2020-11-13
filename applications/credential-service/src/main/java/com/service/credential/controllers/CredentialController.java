package com.service.credential.controllers;

import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.credential.controllers.request.CredentialCreationRequest;
import com.service.credential.services.CreateCredentialService;
import com.service.credential.services.GetOwnerCredentialsService;
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
    private GetOwnerCredentialsService getOwnerCredentialsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("creation")
    public void createCredential(@RequestBody @Validated CredentialCreationRequest credentialCreationRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        createCredentialService.createCredential(credentialCreationRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner-credentials")
    public List<CredentialAsset> getOwnerCredential(@RequestParam("owner_id") Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {
        return getOwnerCredentialsService.getOwnerCredentials(ownerId);
    }

}
