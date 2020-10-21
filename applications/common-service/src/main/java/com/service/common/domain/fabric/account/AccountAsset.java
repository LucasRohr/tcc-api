package com.service.common.domain.fabric.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AccountAsset {
    private Long accountId;

    private String privateKey;

    private String publicKey;
}