package com.service.common.domain.fabric.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserAsset {
    private Long userId;

    private String cpf;

    private LocalDateTime birthday;
}
