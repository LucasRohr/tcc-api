package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateAccountLastUpdatedAtService {

    @Autowired
    private AccountRepository accountRepository;

    public void updateUpdatedAt(Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        account.setUpdatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }

}
