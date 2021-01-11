package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.dto.CredentialResponseWithouPassword;
import com.service.common.dto.HeirAccountResponseDto;
import com.service.common.repository.HeirRepository;
import com.service.user.clients.CredentialClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOwnerHeirsService {

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private CredentialClient credentialClient;

    public List<HeirAccountResponseDto> getHeirs(Long ownerId) {
        List<Heir> list =  heirRepository.getOwnerHeirs(ownerId);

        List<HeirAccountResponseDto> listDto = new ArrayList<>();
        List<CredentialResponseWithouPassword> listCredentials = credentialClient.getCredentialsByHeirsOwner(ownerId);
        list.forEach(heir -> {
            List<CredentialResponseWithouPassword> heirCredentials = listCredentials
                    .stream()
                    .filter(cred -> cred.getHeirsIds().contains(heir.getAccount().getId()))
                    .collect(Collectors.toList());
            HeirAccountResponseDto dto = new HeirAccountResponseDto(
                    heir.getAccount().getId(),
                    heir.getAccount().getUser().getName(),
                    heir.getAccount().getName(),
                    heir.getAccount().getUser().getEmail(),
                    (long) heir.getFileHeirs().size() + heirCredentials.size()
            );
            listDto.add(dto);
            System.out.println(heir.toString());
        });

        System.out.println("\n ===================================== \n");

        return listDto;
    }

}
