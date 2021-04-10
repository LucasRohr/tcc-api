package com.service.common.domain.fabric.file;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileAsset {
    private Long fileId;

    private String symmetricKey;

    private Long createdAt;

    private Long fileOwnerId;
}
