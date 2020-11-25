package com.service.credential.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialCreationRequest {
    private Long credentialId;

    private String name;

    private String login;

    private String password;

    private String link;

    private String description;

    private List<Long> heirsIds;

    private Long ownerId;
}
