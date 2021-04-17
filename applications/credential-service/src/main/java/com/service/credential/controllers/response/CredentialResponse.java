package com.service.credential.controllers.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialResponse {
    private Long credentialId;

    private String name;

    private String description;

    private String link;

    private String login;

    private String password;

    private Long credentialOwnerId;

    private List<Long> heirsIds;

    private Boolean isActive;

    private Long createdAt;

    @JsonIgnore
    private String symmetricKey;
}
