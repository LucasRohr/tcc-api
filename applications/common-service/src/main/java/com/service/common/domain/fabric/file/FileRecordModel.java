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

    private String symmetricKey;

    private Long fileOwnerId;

    private Long createdAt;

    public String[] toArguments() {
        return Stream.of(
            this.fileId.toString(),
            this.symmetricKey,
            this.fileOwnerId.toString(),
            this.createdAt.toString()
        ).toArray(String[]::new);
    }
}
