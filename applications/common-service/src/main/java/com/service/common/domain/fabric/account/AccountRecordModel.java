package com.service.common.domain.fabric.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecordModel {
    private Long accountId;

    private String privateKey;

    private String publicKey;

    private String accountType;

    private String cryptoPassword;

    private Long timestamp;

    public String[] toArguments() {
        return Stream.of(
                this.accountId.toString(),
                this.privateKey,
                this.publicKey,
                this.accountType,
                this.cryptoPassword,
                this.timestamp.toString()
        ).toArray(String[]::new);
    }
}
