package com.service.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPasswordRequest {

    private Long accountId;

    private String cryptoPassword;

}
