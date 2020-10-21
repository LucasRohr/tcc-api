package com.service.user.controller;

import com.service.user.service.account.UpdateAccountLastUpdatedAtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private UpdateAccountLastUpdatedAtService updateAccountLastUpdatedAtService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("last-update")
    public void sendLoginToken(@RequestParam("account_id") Long accountId) {
        updateAccountLastUpdatedAtService.uodateUpdatedAt(accountId);
    }

}
