package com.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateDeathCertificateRequest {

    private Long heirId;

    private String certificateHashCode;

}
