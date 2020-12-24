package com.service.common.dto;


import com.service.common.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileHeirDto {

    private Long id;

    private Long heirId;

    private Long fileId;

    private String fileName;

    private FileTypeEnum type;
}
