package com.service.file.controller.request;

import com.service.common.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMultipleFilesRequest {

    @NotNull
    private Long ownerId;

    @NotNull
    private List<Long> heirsIds;

    @NotNull
    private FileTypeEnum type;

    @NotEmpty
    private String cryptoPassword;

}
