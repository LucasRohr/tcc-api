package com.service.common.domain.fabric.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAsset {
    private Long userId;

    private String cpf;

    private Long birthday;
}
