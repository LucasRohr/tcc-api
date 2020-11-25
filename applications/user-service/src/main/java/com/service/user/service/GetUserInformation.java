package com.service.user.service;

import com.service.common.domain.Account;
import com.service.common.domain.Heir;
import com.service.common.domain.User;
import com.service.common.domain.fabric.user.UserAsset;
import com.service.common.enums.AccountTypes;
import com.service.common.helpers.CryptoUtils;
import com.service.common.repository.HeirRepository;
import com.service.common.service.fabric.user.GetUserAssetByIdService;
import com.service.user.controller.response.AccountResponse;
import com.service.user.dto.UserInformation;
import com.service.common.repository.AccountRepository;
import com.service.common.repository.UserRepository;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetUserInformation {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private GetUserAssetByIdService getUserAssetByIdService;

    public UserInformation getUserInformation(Long id) throws ProposalException, IOException, InvalidArgumentException {
        Optional<User> user = userRepository.findById(id);
        List<Account> accounts = accountRepository.getAllUserAccounts(id);

        UserAsset userAsset = getUserAssetByIdService.getUserAssetById(id);

        LocalDateTime birthday =
                Instant.ofEpochMilli(userAsset.getBirthday()).atZone(ZoneId.systemDefault()).toLocalDateTime();

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

                    if(account.getType() == AccountTypes.HEIR) {
                        Heir heir = heirRepository.getHeirByAccountId(account.getId());
                        accountResponse.setStatus(heir.getStatus());
                    }

                    return accountResponse;
                }
        ).collect(Collectors.toList());

        return new UserInformation(
                id,
                user.get().getEmail(),
                user.get().getName(),
                CryptoUtils.decryptSimpleString(userAsset.getCpf()),
                birthday,
                user.get().getToken(),
                accountsList
        );
    }
}
