package com.service.invite.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteCodeUpdateRequest {

    private Long id;

    private String code;

}
