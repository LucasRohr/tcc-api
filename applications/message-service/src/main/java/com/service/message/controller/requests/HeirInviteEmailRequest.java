package com.service.message.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeirInviteEmailRequest extends EmailRequest {

    @NotEmpty
    private String ownerName;

    @NotNull
    private boolean receiverExists;

    @NotNull
    private Long inviteId;

}
