package com.service.user.service;

import com.service.common.dto.HeirAssetCheckDto;
import com.service.user.clients.CredentialClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAssetsByHeirService {

    @Autowired
    private CredentialClient credentialClient;

    public List<HeirAssetCheckDto> getAssetsByHeir() {
        return credentialClient.getCredentialsByHeir();
    }
}
