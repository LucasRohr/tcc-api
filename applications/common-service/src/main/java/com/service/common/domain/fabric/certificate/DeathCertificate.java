package com.service.common.domain.fabric.certificate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeathCertificate {
    private Long ownerId;

    private Boolean isHeritageActive;

    private String hashCode;
}
