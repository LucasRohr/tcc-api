package com.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FileTypeEnum {
    DOCUMENT("DOCUMENT"),
    IMAGE("IMAGE"),
    VIDEO("VIDEO");

    @Getter
    private String value;
}
