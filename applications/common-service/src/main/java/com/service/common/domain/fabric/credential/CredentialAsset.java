package com.service.common.domain.fabric.credential;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CredentialAsset {
    private Long credentialId;

    private String name;

    private String description;

    private String link;

    private String login;

    private String password;

    private Long ownerId;

    private List<Long> heirsIds;

    private Boolean isActive;

    private Long createdAt;
}
