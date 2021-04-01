package com.service.common.domain.fabric.file;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileAsset {
    private Long fileId;

    // TODO change to SecretKey and call conversion SymmetricKey method
    private String symmetricKey;

    private Long createdAt;
}
