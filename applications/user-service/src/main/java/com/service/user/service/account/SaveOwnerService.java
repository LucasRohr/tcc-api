package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.Owner;
import com.service.common.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class SaveOwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    public void saveOwner(Account account, KeyPair ownerKeys) {
        Owner owner = new Owner(
                true,
                account
        );

        ownerRepository.save(owner);
    }
}
