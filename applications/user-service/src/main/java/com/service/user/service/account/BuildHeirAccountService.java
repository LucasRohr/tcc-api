package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.User;
import com.service.common.enums.AccountTypes;
import com.service.common.repository.UserRepository;
import com.service.user.controller.request.CreateHeirRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class BuildHeirAccountService {

    @Autowired
    private SaveAccountService saveAccountService;

    @Autowired
    private UserRepository userRepository;

    public void buildAccount(CreateHeirRequest createHeirRequest)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
        User user = userRepository.findById(createHeirRequest.getUserId()).get();

        Account newAccount = new Account(
                createHeirRequest.getName(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                user,
                AccountTypes.HEIR
        );

        saveAccountService.saveAccount(newAccount, AccountTypes.HEIR, createHeirRequest.getOwnerId());
    }

}
