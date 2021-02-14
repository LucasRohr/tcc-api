package com.service.credential.controllers;

import com.service.credential.controllers.request.CredentialCreationRequest;
import com.service.credential.controllers.request.CredentialRemoveRequest;
import com.service.common.dto.HeirsUpdateRequest;
import com.service.credential.controllers.response.CredentialHeirResponse;
import com.service.common.dto.CredentialResponseWithouPassword;
import com.service.credential.services.*;
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

    @Autowired
    private RemoveCredentialService removeCredentialService;

    @Autowired
    private GetHeirsForCredentialService getHeirsForCredentialService;

    @Autowired
    private UpdateCredentialHeirsService updateCredentialHeirsService;

    @Autowired
    private GetHeirCredentialsService getHeirCredentialsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("creation")
    public void createCredential(@RequestBody @Validated CredentialCreationRequest credentialCreationRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        createCredentialService.createCredential(credentialCreationRequest, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner-credentials")
    public List<CredentialResponseWithouPassword> getOwnerCredentials(@RequestParam("owner_id") Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {
        return getCredentialsWithoutPasswordService.getCredentials(ownerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("heir-credentials")
    public List<CredentialResponseWithouPassword> getHeirCredentials(@RequestParam("heir_id") Long heirId)
            throws ProposalException, IOException, InvalidArgumentException {
        return getHeirCredentialsService.getHeirCredentials(heirId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("credential-auth")
    public String getCredentialPassword(
            @RequestParam("account_id") Long accountId,
            @RequestParam("credential_id") Long credentialId,
            @RequestParam("is_owner") boolean isOwner
    )
            throws ProposalException, IOException, InvalidArgumentException {
        return getCredentialPasswordService.getPassword(accountId, credentialId, isOwner);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("credential-remove")
    public void removeCredential(@RequestBody CredentialRemoveRequest credentialRemoveRequest)
            throws ProposalException, IOException, InvalidArgumentException {
         removeCredentialService.removeCredential(credentialRemoveRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner/available-heirs")
    public List<CredentialHeirResponse> getHeirs(
            @RequestParam("owner_id") Long ownerId,
            @RequestParam("credential_id") Long credentialId
    ) throws ProposalException, IOException, InvalidArgumentException {
        return getHeirsForCredentialService.getHeirs(ownerId, credentialId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("heirs-update")
    public void updateHeirs(@RequestBody @Validated HeirsUpdateRequest heirsUpdateRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        updateCredentialHeirsService.updateHeirs(heirsUpdateRequest);
    }

}
