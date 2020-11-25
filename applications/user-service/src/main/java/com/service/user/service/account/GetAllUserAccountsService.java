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

    public List<AccountResponse> getAccounts(Long userId) {
        List<Account> accounts = accountRepository.getAllUserAccounts(userId);

        List<AccountResponse> accountsList = accounts.stream().map(
                (account) -> {
                    final AccountResponse accountResponse = new AccountResponse(
                            account.getId(),
                            account.getName(),
                            account.getUpdatedAt(),
                            account.getUser().getId(),
                            account.getUser().getName(),
                            account.getType()
                    );

                    return accountResponse;
                }
        ).collect(Collectors.toList());

        return accountsList;
    }

}
