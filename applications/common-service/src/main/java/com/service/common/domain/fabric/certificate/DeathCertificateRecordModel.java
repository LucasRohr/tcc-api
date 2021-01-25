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

    public String[] toArguments() {
        return Stream.of(
                this.hashCode
        ).toArray(String[]::new);
    }
}
