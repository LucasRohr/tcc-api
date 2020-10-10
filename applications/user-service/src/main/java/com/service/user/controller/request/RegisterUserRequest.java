package com.service.user.controller.request;

import com.service.user.enums.AccountTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    private String name;

    private String email;

    private String cpf;

    private LocalDateTime birthday;

    private String password;

    private String account;

    private AccountTypes firstAccountType;

    private Long ownerId;

}
