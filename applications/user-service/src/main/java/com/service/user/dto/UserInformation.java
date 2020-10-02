package com.service.user.dto;

import com.service.common.domain.Account;
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

    private String cpf;

    private LocalDateTime birthday;

    private String token;

    private List<Account> accounts;

    public UserInformation(Long id, String email, String cpf, LocalDateTime birthday, String token) {
        this.id = id;
        this.email = email;
        this.cpf = cpf;
        this.birthday = birthday;
        this.token = token;
    }

}
