package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.repository.AccountRepository;
import com.service.user.controller.request.InactivateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InactivateAccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void inactivate(InactivateAccountRequest inactivateAccountRequest) {
        Account account = accountRepository.findById(inactivateAccountRequest.getId()).get();

        account.setIsActive(false);

        accountRepository.save(account);
    }

}
