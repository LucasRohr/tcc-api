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

    private Long ownerId;

    private List<Long> heirsIds;

    private Boolean isActive;

    public String[] toArguments() {
        return Stream.of(
                this.credentialId.toString(),
                this.name,
                this.description,
                this.link,
                this.login,
                this.password,
                this.ownerId.toString(),
                this.heirsIds.toString(),
                this.isActive.toString()
        ).toArray(String[]::new);
    }
}
