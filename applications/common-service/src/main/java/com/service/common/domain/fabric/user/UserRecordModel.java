package com.service.common.domain.fabric.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordModel {
    private Long userId;

    private String cpf;

    private LocalDateTime birthday;

    public String[] toArguments() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(this.birthday, ZoneId.systemDefault());
        Long birthday = zonedDateTime.toInstant().toEpochMilli();

        return Stream.of(
                this.userId.toString(),
                this.cpf,
                birthday.toString()
        ).toArray(String[]::new);
    }
}
