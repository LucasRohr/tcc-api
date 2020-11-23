package com.service.credential.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialHeirResponse {

    private Long id;

    private String name;

    private String account;

    private String email;

    private boolean hasItem;

}
