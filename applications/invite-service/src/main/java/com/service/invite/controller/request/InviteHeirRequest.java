package com.service.invite.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteHeirRequest {

    private String email;

    private String phone;

    private Long ownerId;

    private String ownerName;

}
