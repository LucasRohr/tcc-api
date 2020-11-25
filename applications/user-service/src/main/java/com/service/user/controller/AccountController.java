package com.service.user.controller;

import com.service.common.dto.HeirAccountResponseDto;
import com.service.user.controller.request.CreateHeirRequest;
import com.service.user.controller.request.CreateOwnerRequest;
import com.service.user.controller.response.AccountResponse;
import com.service.user.dto.HeirDeactivationRequest;
import com.service.user.service.account.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private UpdateAccountLastUpdatedAtService updateAccountLastUpdatedAtService;

    @Autowired
    private GetOwnerHeirsService getAllOwnerHeirsService;

    @Autowired
    private BuildHeirAccountService buildHeirAccountService;

    @Autowired
    private DeactivateHeirService deactivateHeirService;

    @Autowired
    private CreateSingleOwnerAccountService createSingleOwnerAccountService;

    @Autowired
    private GetAllUserAccountsService getAllUserAccountsService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("last-update")
    public void sendLoginToken(@RequestParam("account_id") Long accountId) {
        updateAccountLastUpdatedAtService.uodateUpdatedAt(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("all-accounts")
    public List<AccountResponse> getAllUserAccounts(@RequestParam("user_id") Long userId) {
        return getAllUserAccountsService.getAccounts(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner/heirs")
    public List<HeirAccountResponseDto> getOwnerHeirs(@RequestParam("owner_id") Long ownerId) {
        return getAllOwnerHeirsService.getHeirs(ownerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("owner/heir-remove")
    public boolean deactivateHeir(@RequestBody HeirDeactivationRequest request) {
        return deactivateHeirService.deactivateHeir(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("heir/heir-creation")
    public void createHeir(@RequestBody CreateHeirRequest createHeirRequest)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
        buildHeirAccountService.buildAccount(createHeirRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("owner/owner-creation")
    public void createOwner(@RequestBody CreateOwnerRequest createOwnerRequest)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
        createSingleOwnerAccountService.createOwner(createOwnerRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner/credential-heirs")
    public List<HeirAccountResponseDto> getHeirsForCredentialCreation(@RequestParam("owner_id") Long ownerId) {
        return getAllOwnerHeirsService.getHeirs(ownerId);
    }

}
