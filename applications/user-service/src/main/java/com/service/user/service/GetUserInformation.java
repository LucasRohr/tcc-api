package com.service.user.service;

import com.service.common.domain.Account;
import com.service.common.domain.User;
import com.service.user.dto.UserInformation;
import com.service.common.repository.AccountRepository;
import com.service.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GetUserInformation {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public UserInformation getUserInformation(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<Account> accounts = accountRepository.getAllUserAccounts(id);

        UserInformation userInformation = new UserInformation(
                id,
                user.get().getEmail(),
                user.get().getEmail(),
                LocalDateTime.now(),
                user.get().getToken()
        );

        userInformation.setAccounts(accounts);

        return userInformation;
    }
}
