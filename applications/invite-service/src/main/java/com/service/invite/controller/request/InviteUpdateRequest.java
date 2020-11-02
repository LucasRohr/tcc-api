package com.service.invite.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteUpdateRequest {

    private Long inviteId;

    private boolean accepted;

}
