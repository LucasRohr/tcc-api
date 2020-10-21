package com.service.invite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsInviteDto {

    @NotEmpty
    private String phone;

    @NotEmpty
    private String ownerName;

    private boolean receiverExists;

    @NotNull
    private Long inviteId;

}
