package com.service.user.controller;

import com.service.common.dto.HeirAccountResponseDto;
import com.service.common.dto.HeirAssetCheckDto;
import com.service.user.controller.request.*;
import com.service.user.controller.response.AccountResponse;
import com.service.user.dto.HeirDeactivationRequest;
import com.service.user.dto.UpdateHeirHeritageRequest;
import com.service.user.service.GetCheckAssetsByHeirService;
import com.service.user.service.UpdateHeirHeritagesService;
import com.service.user.service.account.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

    @Autowired
    private UpdateAccountService updateAccountService;

    @Autowired
    private InactivateAccountService inactivateAccountService;

    @Autowired
    private GetCheckAssetsByHeirService getCheckAssetsByHeirService;

    @Autowired
    private UpdateHeirHeritagesService updateHeirHeritagesService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("last-update")
    public void updateLastAccess(@RequestParam("account_id") Long accountId) {
        updateAccountLastUpdatedAtService.updateUpdatedAt(accountId);
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

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("account-update")
    public void updateAccount(@RequestBody @Validated AccountUpdateRequest accountUpdateRequest) {
        updateAccountService.update(accountUpdateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("account-inactivation")
    public void inactivateUser(@RequestBody @Validated InactivateAccountRequest inactivateAccountRequest) {
        inactivateAccountService.inactivate(inactivateAccountRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("heir/heir-heritages")
    public List<HeirAssetCheckDto> getHeirCheckAssets(@RequestParam("heir_id") Long heirId) {
        return getCheckAssetsByHeirService.getCheckAssetsByHeir(heirId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("heir/items-update")
    public void updateHeirItems(
            @RequestParam("heir_id") Long heirId,
            @RequestBody UpdateHeirHeritageRequest request
    ) {
        updateHeirHeritagesService.updateHeirHeritages(heirId, request);
    }

}
