package com.service.common.domain.fabric.credential;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CredentialAsset {
    private Long credentialId;

    private String name;

    private String description;

    private String link;

    private String login;

    private String password;

    private Long credentialOwnerId;

    private String heirsIds;

    private Boolean isActive;

    private Long createdAt;

    private String symmetricKey;
}
