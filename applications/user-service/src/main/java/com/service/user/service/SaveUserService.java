package com.service.user.service;

import com.service.common.domain.Account;
import com.service.common.domain.User;
import com.service.common.domain.fabric.user.UserRecordModel;
import com.service.common.repository.UserRepository;
import com.service.common.service.fabric.user.SaveUserAssetService;
import com.service.user.config.PasswordEncoder;
import com.service.user.controller.request.RegisterUserRequest;
import com.service.user.service.account.SaveAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SaveUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaveUserAssetService saveUserAssetService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SaveAccountService saveAccountService;

    public void saveUser(RegisterUserRequest registerUserRequest) throws Exception {
        User user = new User(
                registerUserRequest.getName(),
                registerUserRequest.getEmail(),
                encoder.bCryptPasswordEncoder().encode(registerUserRequest.getPassword()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        Account newAccount = new Account(
                registerUserRequest.getAccount(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                savedUser
        );

        saveAccountService.saveAccount(newAccount, registerUserRequest.getFirstAccountType(), registerUserRequest.getOwnerId());

        UserRecordModel userRecordModel = new UserRecordModel(
                savedUser.getId(),
                registerUserRequest.getCpf(),
                registerUserRequest.getBirthday()
        );

        saveUserAssetService.createTransaction(userRecordModel);
    }
}
