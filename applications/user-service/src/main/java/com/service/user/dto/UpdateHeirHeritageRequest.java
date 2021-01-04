package com.service.user.dto;

import com.service.common.dto.HeirsUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHeirHeritageRequest {

    private List<Long> fileHeirIds;

    private List<HeirsUpdateRequest> credentialsUpdateRequests;

}
