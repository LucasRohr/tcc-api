package com.service.message.controller.requests;

import com.service.message.enums.EmailTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    @NotEmpty
    private String telephone;

    @NotNull
    private EmailTypes type;

    @NotEmpty
    private String ownerName;

}
