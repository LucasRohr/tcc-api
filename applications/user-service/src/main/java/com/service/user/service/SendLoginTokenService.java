package com.service.user.service;

import com.service.common.domain.User;
import com.service.user.dto.LoginAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendLoginTokenService {

    @Autowired
    private UpdateUserService updateUserService;

    @Autowired
    private GetUserByEmailService getUserByEmailService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOGIN_MESSAGE_KEY = "LOGIN_AUTH";
    private static final String LOGIN_MESSAGE_URL = "http://message-service/messages/email/login-auth";

    public void sendToken(String email, String token) {
        User user = getUserByEmailService.getUserByEmail(email);
        user.setToken(token);
        updateUserService.updateUser(user);

        LoginAuthRequest loginAuthRequest = new LoginAuthRequest(email, LOGIN_MESSAGE_KEY);
        restTemplate.postForObject(LOGIN_MESSAGE_URL, loginAuthRequest, LoginAuthRequest.class);
    }

}
