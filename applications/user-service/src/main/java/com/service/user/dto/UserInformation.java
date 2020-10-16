package com.service.user.dto;

import com.service.user.controller.response.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {

    private Long id;

    private String email;

    private String name;

    private String cpf;

    private LocalDateTime birthday;

    private String token;

    private List<AccountResponse> accounts;
}
