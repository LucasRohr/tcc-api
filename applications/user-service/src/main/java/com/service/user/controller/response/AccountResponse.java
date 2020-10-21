package com.service.user.controller.response;

import com.service.common.enums.AccountTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long id;

    private String name;

    private LocalDateTime updatedAt;

    private Long userId;

    private AccountTypes type;

}
