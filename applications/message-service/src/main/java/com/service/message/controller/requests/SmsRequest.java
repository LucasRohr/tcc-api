package com.service.message.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    @NotEmpty
    private String phone;

    @NotEmpty
    private String ownerName;

    private boolean receiverExists;

    @NotNull
    private Long inviteId;

}
