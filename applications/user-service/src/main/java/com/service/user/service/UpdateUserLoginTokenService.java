package com.service.user.service;

import com.service.common.domain.User;
import com.service.common.repository.UserRepository;
import com.service.common.dto.UpdateUserLoginTokenRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserLoginTokenService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetUserByEmailService getUserByEmailService;

    public User updateUser(UpdateUserLoginTokenRequestDto updateUserLoginTokenRequestDto) {
        User user = getUserByEmailService.getUserByEmail(updateUserLoginTokenRequestDto.getEmail());

        if(user != null) {
            user.setLoginToken(updateUserLoginTokenRequestDto.getLoginToken());
        }

        return userRepository.save(user);
    }
}
