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
public class UpdateFileRequest {

    @NotNull
    private Long id;

    @NotNull
    private Long ownerId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private List<Long> heirsIds;

}
