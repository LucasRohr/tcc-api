package com.service.user.service;

import com.service.user.clients.CredentialClient;
import com.service.user.clients.FileClient;
import com.service.user.dto.UpdateHeirHeritageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateHeirHeritagesService {

    @Autowired
    private FileClient fileClient;

    @Autowired
    private CredentialClient credentialClient;

    public void updateHeirHeritages(Long heirId, UpdateHeirHeritageRequest request) {
        fileClient.unlinkFileHeirs(heirId, request.getFileHeirIds());
        request.getCredentialsUpdateRequests().forEach(credentialRequest -> {
            credentialClient.editCredentialHeirs(credentialRequest);
        });
    }

}
