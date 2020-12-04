package com.service.file.controller.request;

import com.service.common.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMultipleFilesRequest {

    private Long ownerId;

    private List<Long> heirsIds;

    private FileTypeEnum type;

}
