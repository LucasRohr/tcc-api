package com.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeirDeactivationRequest {
    @NotNull
    private Long heirId;

    @NotNull
    private Long ownerId;
}
