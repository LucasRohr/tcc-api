package com.service.user.clients;

import com.service.common.dto.HeirAssetCheckDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "credential", url = "http://localhost:8500")
public interface CredentialClient {

    @RequestMapping(method = RequestMethod.GET, value = "/credentials/boiola")
    List<HeirAssetCheckDto> getCredentialsByHeir();
}
