package com.service.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHeirRequest {

    private String name;

    private Long userId;

    private Long ownerId;

    private String cryptoPassword;

}
