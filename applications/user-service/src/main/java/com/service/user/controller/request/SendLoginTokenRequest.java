package com.service.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendLoginTokenRequest {

    @NotNull
    private String email;

    @NotNull
    private String token;

}
