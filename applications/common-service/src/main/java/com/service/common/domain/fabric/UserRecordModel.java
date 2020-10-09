package com.service.common.domain.fabric;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordModel {
    private Long userId;

    private String cpf;

    private LocalDateTime birthday;

    private String privateKey;

    public String[] toArguments() {
        return Stream.of(
                this.userId.toString(),
                this.cpf,
                this.birthday.toString(),
                this.privateKey
        ).toArray(String[]::new);
    }
}
