package com.service.user.service;

import com.service.common.domain.User;
import com.service.user.exceptions.ValidateLoginException;
import com.service.common.repository.UserRepository;
import com.service.user.controller.response.LoginTokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateLoginTokenService {
    @Autowired
    private UserRepository userRepository;

    public LoginTokenValidationResponse validateToken(String loginToken) throws ValidateLoginException {
        User user = userRepository.validateUserLoginToken(loginToken);

        if(user == null) {
            throw new ValidateLoginException();
        }

        return new LoginTokenValidationResponse(user.getId());
    }
}
