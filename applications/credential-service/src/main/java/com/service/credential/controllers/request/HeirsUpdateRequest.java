package com.service.credential.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeirsUpdateRequest {

    private Long ownerId;

    private Long credentialId;

    private List<Long> heirsIds;

}
