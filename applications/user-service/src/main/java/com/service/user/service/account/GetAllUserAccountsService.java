package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.repository.AccountRepository;
import com.service.user.controller.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUserAccountsService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllUserAccounts(Long id) {
        return accountRepository.getAllUserAccounts(id);
    }
}
