package com.service.credential.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialRemoveRequest {

    private Long ownerId;

    private Long credentialId;

    private String cryptoPassword;

}
