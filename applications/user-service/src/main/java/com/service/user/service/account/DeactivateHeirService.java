package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.enums.HeirStatusEnum;
import com.service.common.repository.HeirRepository;
import com.service.user.dto.HeirDeactivationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeactivateHeirService {

    @Autowired
    private HeirRepository heirRepository;

    public boolean deactivateHeir(HeirDeactivationRequest request) {
        System.out.println(request);
        Heir heir = heirRepository.getHeirByIdAndOwner(request.getOwnerId(), request.getHeirId());
        System.out.println(heir);
        if(heir != null) {
            heir.setStatus(HeirStatusEnum.DISINHERITED);
            heirRepository.save(heir);
            return true;
        }
        return false;
    }
}
