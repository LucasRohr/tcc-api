package com.service.user.controller.response;

import com.service.common.enums.AccountTypes;
import com.service.common.enums.HeirStatusEnum;
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

    private String userName;

    private AccountTypes type;

    private HeirStatusEnum status;

    private String ownerName;

    public AccountResponse(
            Long id,
            String name,
            LocalDateTime updatedAt,
            Long userId,
            String userName,
            AccountTypes type
    ) {
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.userName = userName;
        this.type = type;
    }

}
