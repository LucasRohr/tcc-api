package com.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HeirStatusEnum {
    ACTIVE("active"),
    ACCEPTED("accepted"),
    DISINHERITED("disinherited");

    @Getter
    private String value;
}
