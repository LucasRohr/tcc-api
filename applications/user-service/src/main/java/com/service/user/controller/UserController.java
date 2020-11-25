package com.service.user.controller;

import com.service.common.domain.User;
import com.service.common.enums.AccountTypes;
import com.service.user.controller.request.RegisterUserRequest;
import com.service.user.controller.request.SendLoginTokenRequest;
import com.service.user.controller.request.ValidateLoginTokenRequest;
import com.service.user.dto.UserInformation;
import com.service.user.controller.response.LoginTokenValidationResponse;
import com.service.user.exceptions.UserAlreadyExistsException;
import com.service.user.service.*;
import com.service.user.service.account.GetAllUserAccountsService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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

    @Autowired
    private GetAllUserAccountsService getAllUserAccountsService;

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler({ UserAlreadyExistsException.class })
    @PostMapping("register")
    public void registerUser(@RequestBody @Validated RegisterUserRequest registerUserRequest)
            throws UserAlreadyExistsException, NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("bootstrap")
    public void bootstrap() throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException, IOException {
        registerUser(new RegisterUserRequest(
                "Carlos Vasconcellos",
                "scorpionmk1302@gmail.com",
                "354.336.510-02",
                LocalDateTime.now().minusYears(29),
                "Literatura2012",
                "Papai Carlos",
                AccountTypes.OWNER,
                null
        ));
        List<User> users = allUsersService.getAllUsers();
        Long carlosId = users.get(users.size() - 1).getId();
        Long ownerId = getAllUserAccountsService.getAllUserAccounts(carlosId).get(0).getId();
        // Long oooo = getUserInformation.getUserInformation(carlosId).getAccounts().get(0).getId();
        saveUserService.saveUser(new RegisterUserRequest(
                "Ronaldo Marques",
                "heeynityxdx235@gmail.com",
                "882.338.300-54",
                LocalDateTime.now().minusYears(34),
                "Literatura2012",
                "Maridão Show De Horror",
                AccountTypes.HEIR,
                ownerId
        ));
    }
}
