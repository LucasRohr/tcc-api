package com.service.user.service;

import com.service.common.domain.User;
import com.service.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserByEmailService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
