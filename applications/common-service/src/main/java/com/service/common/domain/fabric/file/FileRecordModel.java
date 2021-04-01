package com.service.common.domain.fabric.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileRecordModel {
    private Long fileId;

    // TODO change to SecretKey and call conversion SymmetricKey method
    private String symmetricKey;

    private Long createdAt;

    public String[] toArguments() {
        return Stream.of(
            this.fileId.toString(),
            this.symmetricKey,
            this.createdAt.toString()
        ).toArray(String[]::new);
    }
}
