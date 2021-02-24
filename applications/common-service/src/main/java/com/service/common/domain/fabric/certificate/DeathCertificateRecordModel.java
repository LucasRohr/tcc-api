package com.service.common.domain.fabric.certificate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeathCertificateRecordModel {

    private String hashCode;

    private Long ownerId;

    public String[] toArguments() {
        return Stream.of(
                this.hashCode,
                this.ownerId
        ).toArray(String[]::new);
    }
}
