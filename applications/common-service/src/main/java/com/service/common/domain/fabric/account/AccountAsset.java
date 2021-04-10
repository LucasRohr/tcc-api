package com.service.common.domain.fabric.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountAsset {
    private Long accountId;

    private String privateKey;

    private String publicKey;

    private String accountType;

    private String cryptoPassword;

    private Long timestamp;
}
