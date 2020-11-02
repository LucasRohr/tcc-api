package com.service.user.controller;

import com.service.common.dto.HeirAccountResponseDto;
import com.service.user.controller.request.CreateHeirRequest;
import com.service.user.service.account.BuildHeirAccountService;
import com.service.user.service.account.GetAllOwnerHeirsService;
import com.service.user.service.account.SaveAccountService;
import com.service.user.service.account.UpdateAccountLastUpdatedAtService;
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
    private GetAllOwnerHeirsService getAllOwnerHeirsService;

    @Autowired
    private BuildHeirAccountService buildHeirAccountService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("last-update")
    public void sendLoginToken(@RequestParam("account_id") Long accountId) {
        updateAccountLastUpdatedAtService.uodateUpdatedAt(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner/heirs")
    public List<HeirAccountResponseDto> getOwnerHeirs(@RequestParam("owner_id") Long ownerId) {
        return getAllOwnerHeirsService.getHeirs(ownerId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("heir/heir-creation")
    public void createHeir(@RequestBody CreateHeirRequest createHeirRequest)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
        buildHeirAccountService.buildAccount(createHeirRequest);
    }

}
