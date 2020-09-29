package com.service.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FileTypeEnum {
    DOCUMENT("document"),
    IMAGE("image"),
    VIDEO("video");

    @Getter
    private String value;
}
