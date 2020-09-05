package com.service.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTypes {
    HEIR_INVITE("heir-invite"),
    LOGIN_AUTH("login-auth"),
    INVITE_ACCEPTED("heir-invite"),
    OWNER_DEATH("heir-invite");

    private String templateName;
}