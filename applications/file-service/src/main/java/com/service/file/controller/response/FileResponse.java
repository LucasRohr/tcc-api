package com.service.file.controller.response;

import com.service.common.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {

    private Long id;

    private String name;

    private String description;

    private FileTypeEnum type;

    private String file;

    private String mimeType;

}
