package com.service.credential.controllers;

import com.service.credential.controllers.request.CredentialCreationRequest;
import com.service.credential.services.CreateCredentialService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CreateCredentialService createCredentialService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("creation")
    public void createCredential(@RequestBody CredentialCreationRequest credentialCreationRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        createCredentialService.createCredential(credentialCreationRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner-credentials")
    public void getOwnerCredential(@RequestParam("owner_id") Long ownerId) {
//        createCredentialService.createCredential(credentialCreationRequest);
    }

}
