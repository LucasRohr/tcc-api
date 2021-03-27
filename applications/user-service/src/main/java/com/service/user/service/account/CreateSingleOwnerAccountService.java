package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.User;
import com.service.common.enums.AccountTypes;
import com.service.common.repository.UserRepository;
import com.service.user.controller.request.CreateOwnerRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class CreateSingleOwnerAccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaveAccountService saveAccountService;

    public void createOwner(CreateOwnerRequest createOwnerRequest)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {

        User user = userRepository.findById(createOwnerRequest.getUserId()).get();

        Account ownerAccount = new Account(
                createOwnerRequest.getAccountName(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                user,
                AccountTypes.OWNER
        );

        saveAccountService.saveAccount(ownerAccount, AccountTypes.OWNER, createOwnerRequest.getUserId(), "");
    }

}
