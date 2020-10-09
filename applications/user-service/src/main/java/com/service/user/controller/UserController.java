package com.service.user.controller;

import com.service.common.domain.User;
import com.service.user.controller.request.RegisterUserRequest;
import com.service.user.controller.request.SendLoginTokenRequest;
import com.service.user.controller.request.ValidateLoginTokenRequest;
import com.service.user.dto.UserInformation;
import com.service.user.controller.response.LoginTokenValidationResponse;
import com.service.user.service.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private AllUsersService allUsersService;

    @Autowired
    private ValidateLoginTokenService validateLoginTokenService;

    @Autowired
    private GetUserInformation getUserInformation;

    @Autowired
    private SendLoginTokenService sendLoginTokenService;

    @Autowired
    private SaveUserService saveUserService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public void registerUser(@RequestBody @Validated RegisterUserRequest registerUserRequest) throws Exception {
        saveUserService.saveUser(registerUserRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("all-users")
    public List<User> getAllUsers() {
        return allUsersService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("login-token-send")
    public void sendLoginToken(@RequestBody @Validated SendLoginTokenRequest sendLoginTokenRequest) {
        sendLoginTokenService.sendToken(sendLoginTokenRequest.getEmail(), sendLoginTokenRequest.getToken());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("login-token-validation")
    public LoginTokenValidationResponse validateLoginToken(@RequestBody @Validated ValidateLoginTokenRequest validateLoginTokenRequest) {
        return validateLoginTokenService.validateToken(validateLoginTokenRequest.getLoginToken());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user-info")
    public UserInformation getUserInfo(@RequestParam("user_id") Long id) throws ProposalException, IOException, InvalidArgumentException {
        return getUserInformation.getUserInformation(id);
    }
}
