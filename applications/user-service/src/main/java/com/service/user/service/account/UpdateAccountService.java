package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.repository.AccountRepository;
import com.service.user.controller.request.AccountUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateAccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void update(AccountUpdateRequest accountUpdateRequest) {
        Account account = accountRepository.findById(accountUpdateRequest.getAccountId()).get();

        account.setName(accountUpdateRequest.getAccountName());
        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }

}
