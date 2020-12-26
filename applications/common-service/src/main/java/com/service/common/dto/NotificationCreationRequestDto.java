package com.service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreationRequestDto {

    @NotNull
    private Long accountId;

    @NotNull
    private Long receiverId;

    @NotEmpty
    private String type;

}
