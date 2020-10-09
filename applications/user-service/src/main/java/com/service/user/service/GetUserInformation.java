package com.service.user.service;

import com.service.common.domain.Account;
import com.service.common.domain.User;
import com.service.common.domain.fabric.UserAsset;
import com.service.common.service.fabric.GetUserAssetByIdService;
import com.service.user.dto.UserInformation;
import com.service.common.repository.AccountRepository;
import com.service.common.repository.UserRepository;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GetUserInformation {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GetUserAssetByIdService getUserAssetByIdService;

    public UserInformation getUserInformation(Long id) throws ProposalException, IOException, InvalidArgumentException {
        Optional<User> user = userRepository.findById(id);
        List<Account> accounts = accountRepository.getAllUserAccounts(id);

        UserAsset userAsset = getUserAssetByIdService.getUserAssetById(id);

        UserInformation userInformation = new UserInformation(
                id,
                user.get().getEmail(),
                userAsset.getCpf(),
                userAsset.getBirthday(),
                user.get().getToken()
        );

        userInformation.setAccounts(accounts);

        return userInformation;
    }
}
