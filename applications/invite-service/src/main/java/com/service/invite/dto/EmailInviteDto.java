package com.service.invite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailInviteDto {

    private String email;

    private String type;

    private String ownerName;

    private boolean receiverExists;

    private Long inviteId;

}
