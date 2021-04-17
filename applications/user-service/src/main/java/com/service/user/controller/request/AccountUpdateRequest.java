package com.service.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequest {

    @NotNull
    private Long accountId;

    @NotEmpty
    private String accountName;

    private String cryptoPassword;

    private String newCryptoPassword;

}
