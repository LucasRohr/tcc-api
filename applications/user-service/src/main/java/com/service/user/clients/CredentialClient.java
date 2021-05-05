package com.service.user.clients;

import com.service.common.dto.CredentialResponseWithouPassword;
import com.service.common.dto.HeirsUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "credential", url = "https://localhost:8500/credentials")
public interface CredentialClient {

    @RequestMapping(method = RequestMethod.GET, value = "/owner-credentials?owner_id={ownerId}")
    List<CredentialResponseWithouPassword> getCredentialsByHeirsOwner(@PathVariable("ownerId") Long ownerId);

    @RequestMapping(method = RequestMethod.PUT, value = "/heirs-update")
    void editCredentialHeirs(@RequestBody HeirsUpdateRequest request);

    @RequestMapping(method = RequestMethod.PUT, value = "/release?owner_id={ownerId}")
    void releaseCredentialsHeritage(@PathVariable("ownerId") Long ownerId);
}
