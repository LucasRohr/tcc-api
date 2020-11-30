package com.service.user.service;

import com.service.common.domain.User;
import com.service.common.repository.UserRepository;
import com.service.user.controller.request.InactivateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InactivateUserService {

    @Autowired
    private UserRepository userRepository;

    public void inactivateUser(InactivateUserRequest inactivateUserRequest) {
        User user = userRepository.findById(inactivateUserRequest.getId()).get();

        user.setIsActive(false);

        userRepository.save(user);
    }

}
