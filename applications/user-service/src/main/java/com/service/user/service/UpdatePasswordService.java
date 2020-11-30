package com.service.user.service;

import com.service.common.domain.User;
import com.service.common.repository.UserRepository;
import com.service.user.config.PasswordEncoder;
import com.service.user.controller.request.UpdatePasswordRequest;
import com.service.user.exceptions.InvalidPasswordException;
import com.service.user.exceptions.SamePasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User user = userRepository.findById(updatePasswordRequest.getId()).get();

        boolean isCurrentPasswordValid =
                bCryptEncoder.matches(updatePasswordRequest.getCurrentPassword(), user.getPassword());

        if(!isCurrentPasswordValid) {
            throw new InvalidPasswordException();
        }

        boolean isSamePassword =
                bCryptEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword());

        if(isSamePassword) {
            throw new SamePasswordException();
        }

        String newEncodedPassword = encoder.bCryptPasswordEncoder().encode(updatePasswordRequest.getNewPassword());
        user.setPassword(newEncodedPassword);

        userRepository.save(user);
    }

}
