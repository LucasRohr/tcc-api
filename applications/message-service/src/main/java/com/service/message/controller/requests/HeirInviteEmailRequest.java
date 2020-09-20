package com.service.message.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeirInviteEmailRequest extends EmailRequest {

    @NotEmpty
    private String ownerName;

}
