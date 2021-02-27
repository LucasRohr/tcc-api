package com.service.user.service.account;

import com.service.common.domain.Owner;
import com.service.common.repository.OwnerRepository;
import com.service.user.clients.CredentialClient;
import com.service.user.clients.FileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassOwnerAwayService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private FileClient fileClient;

    @Autowired
    private CredentialClient credentialClient;

    public void passAway(Owner owner) {
        if (owner.getIsAlive()) {
            owner.setIsAlive(false);
            ownerRepository.save(owner);
        }
    }
}
