package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.Heir;
import com.service.common.enums.AccountTypes;
import com.service.common.repository.AccountRepository;
import com.service.common.repository.HeirRepository;
import com.service.user.controller.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUserAccountsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HeirRepository heirRepository;

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

                    if(account.getType().equals(AccountTypes.HEIR)) {
                        Heir heir = heirRepository.getHeirByAccountId(account.getId());
                        accountResponse.setStatus(heir.getStatus());
                        accountResponse.setOwnerName(heir.getOwner().getAccount().getName());
                    }

                    return accountResponse;
                }
        ).collect(Collectors.toList());

        return accountsList;
    }
}
