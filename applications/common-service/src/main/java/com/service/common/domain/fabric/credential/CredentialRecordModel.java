package com.service.common.domain.fabric.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialRecordModel {
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

    public String[] toArguments() {
        return Stream.of(
                this.credentialId.toString(),
                this.name,
                this.description,
                this.link,
                this.login,
                this.password,
                this.credentialOwnerId.toString(),
                this.heirsIds,
                this.isActive.toString(),
                this.createdAt.toString(),
                symmetricKey
        ).toArray(String[]::new);
    }
}
