package com.service.user.service;

import com.service.user.clients.CredentialClient;
import com.service.user.clients.FileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateHeirHeritagesService {

    @Autowired
    private FileClient fileClient;

    @Autowired
    private CredentialClient credentialClient;

    public void updateHeirHeritages(Long heirId, Long[] fileHeirIds) {
        fileClient.unlinkFileHeirs(heirId, fileHeirIds);

    }

}
