package com.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HeirStatusEnum {
    ACTIVE("ACTIVE"),
    ACCEPTED("ACCEPTED"),
    DISINHERITED("DISINHERITED");

    @Getter
    private String value;
}
