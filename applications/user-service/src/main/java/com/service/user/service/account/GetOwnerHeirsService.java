package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.dto.HeirAccountResponseDto;
import com.service.common.repository.HeirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOwnerHeirsService {

    @Autowired
    private HeirRepository heirRepository;

    public List<HeirAccountResponseDto> getHeirs(Long ownerId) {
        List<Heir> list =  heirRepository.getOwnerHeirs(ownerId);
        List<HeirAccountResponseDto> listDto = new ArrayList<>();
        list.forEach(heir -> {
            HeirAccountResponseDto dto = new HeirAccountResponseDto(
                    heir.getAccount().getId(),
                    heir.getAccount().getUser().getName(),
                    heir.getAccount().getName(),
                    heir.getAccount().getUser().getEmail(),
                    (long) heir.getFileHeirs().size()
            );
            listDto.add(dto);
        });
        return listDto;
    }

}
