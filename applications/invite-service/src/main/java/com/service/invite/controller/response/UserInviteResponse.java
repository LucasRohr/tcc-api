package com.service.invite.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInviteResponse {

    private Long id;

    private String senderName;

    private Long ownerId;

}
