package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetHeirsByOwnerService {
    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Heir> getHeirsByOwner(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        return heirRepository.getHeirByOwner(owner.get());
    }

}
