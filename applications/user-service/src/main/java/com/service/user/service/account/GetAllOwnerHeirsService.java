package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.dto.HeirAccountResponseDto;
import com.service.common.repository.HeirRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GetAllOwnerHeirsService {

    @Autowired
    private HeirRepository heirRepository;

    public List<HeirAccountResponseDto> getHeirs(Long ownerId) {
        List<Heir> list = heirRepository.getAllOwnerHeirs(ownerId);

        System.out.println("\n ===================================== \n");

        System.out.println(list.getClass());
        list.forEach(heir -> {
            System.out.println(heir.toString());
        });

        System.out.println("\n ===================================== \n");

        return new ArrayList<>();
    }
}
