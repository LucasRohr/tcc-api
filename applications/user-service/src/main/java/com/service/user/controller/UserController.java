package com.service.user.controller;

import com.service.common.domain.User;
import com.service.common.dto.UpdateUserLoginTokenRequestDto;
import com.service.common.enums.AccountTypes;
import com.service.user.controller.request.*;
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

    @Autowired
    private UpdateProfileService updateProfileService;

    @Autowired
    private UpdatePasswordService updateProfileRequest;

    @Autowired
    private InactivateUserService inactivateUserService;

    @Autowired
    private GetUserByEmailService getUserByEmailService;

    @Autowired
    private UpdateUserLoginTokenService updateUserLoginTokenService;

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
    public UserInformation getUserInfo(@RequestParam("user_id") Long id)
            throws ProposalException, IOException, InvalidArgumentException {
        return getUserInformation.getUserInformation(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user-by-email")
    public User getUserByEmail(@RequestParam("email") String email) {
        return getUserByEmailService.getUserByEmail(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("profile-update")
    public void updateUser(@RequestBody @Validated UpdateProfileRequest updateProfileRequest)
            throws ProposalException, InvalidArgumentException {
        updateProfileService.updateProfile(updateProfileRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("password-update")
    public void updatePassword(@RequestBody @Validated UpdatePasswordRequest updatePasswordRequest) {
        updateProfileRequest.updatePassword(updatePasswordRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("user-inactivation")
    public void inactivateUser(@RequestBody @Validated InactivateUserRequest inactivateUserRequest) {
        inactivateUserService.inactivateUser(inactivateUserRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("login-token-update")
    public void updateUserLoginToken(@RequestBody UpdateUserLoginTokenRequestDto updateUserLoginTokenRequestDto) {
        updateUserLoginTokenService.updateUser(updateUserLoginTokenRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("bootstrap")
    public void bootstrap() throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException, IOException {
        registerUser(new RegisterUserRequest(
                "Carlos Vasconcellos",
                "douglas.dudu2001@hotmail.com",
                "354.336.510-02",
                LocalDateTime.now().minusYears(29),
                "Literatura2012",
                "Carlos Vasconcellos",
                AccountTypes.OWNER,
                null
        ));
        List<User> users = allUsersService.getAllUsers();
        Long carlosId = users.get(users.size() - 1).getId();
        Long ownerId = getAllUserAccountsService.getAccounts(carlosId).get(0).getId();
        saveUserService.saveUser(new RegisterUserRequest(
                "Ronaldo Marques",
                "oliveira.douglasedu@outlook.com",
                "882.338.300-54",
                LocalDateTime.now().minusYears(34),
                "Senha123",
                "Ronaldo Marques",
                AccountTypes.HEIR,
                ownerId
        ));
    }
}
