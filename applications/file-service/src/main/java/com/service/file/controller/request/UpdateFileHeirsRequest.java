package com.service.file.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFileHeirsRequest {

    @NotNull
    private Long id;

    @NotNull
    private List<Long> selectedHeirsIds;

}
