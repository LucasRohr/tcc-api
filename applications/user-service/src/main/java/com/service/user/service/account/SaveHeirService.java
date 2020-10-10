package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.common.enums.HeirStatusEnum;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SaveHeirService {

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public void saveHeir(Account account, KeyPair ownerKeys, Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);

        Heir heir = new Heir(
                HeirStatusEnum.ACCEPTED,
                owner.get(),
                account,
                new ArrayList<>()
        );

        heirRepository.save(heir);
    }

}
