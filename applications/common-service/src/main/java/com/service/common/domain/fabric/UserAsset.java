package com.service.common.domain.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserAsset {
    private Long userId;

    private String cpf;

    private LocalDateTime birthday;

    private String privateKey;

    private String storageHash;
}
