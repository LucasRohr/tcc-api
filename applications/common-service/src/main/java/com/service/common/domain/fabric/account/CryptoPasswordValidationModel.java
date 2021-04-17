package com.service.common.domain.fabric.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPasswordValidationModel {

    private Long accountId;

    private String cryptoPassword;

    public String[] toArguments() {
        return Stream.of(
                this.accountId.toString(),
                this.cryptoPassword
        ).toArray(String[]::new);
    }
}
