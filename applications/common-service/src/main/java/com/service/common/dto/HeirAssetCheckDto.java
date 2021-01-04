package com.service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HeirAssetCheckDto {

    private Long id;
    private String type;
    private String name;

}
