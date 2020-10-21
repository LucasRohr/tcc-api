package com.service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeirAccountResponseDto {

    private Long id;

    private String name;

    private String accountName;

    private String email;

    private Long heritageItemsTotal;

}