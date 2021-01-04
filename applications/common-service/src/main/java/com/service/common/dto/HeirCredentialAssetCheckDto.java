package com.service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HeirCredentialAssetCheckDto extends HeirAssetCheckDto {

    public HeirCredentialAssetCheckDto(Long id, String name, List<Long> credentialHeirsIds) {
        super(id, "CREDENTIAL", name);
        this.credentialHeirsIds = credentialHeirsIds;
    }

    private List<Long> credentialHeirsIds;

}
