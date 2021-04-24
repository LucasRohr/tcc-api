package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.dto.NotificationCreationRequestDto;
import com.service.common.enums.HeirStatusEnum;
import com.service.common.enums.NotificationTypesEnum;
import com.service.common.repository.HeirRepository;
import com.service.user.dto.HeirDeactivationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeactivateHeirService {

    private static final String NOTIFICATION_URL =
            "https://notification-service/notification-creation";

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private RestTemplate restTemplate;

    public boolean deactivateHeir(HeirDeactivationRequest request) {
        Heir heir = heirRepository.getHeirByIdAndOwner(request.getOwnerId(), request.getHeirId());

        if(heir != null) {
            heir.setStatus(HeirStatusEnum.DISINHERITED);
            heirRepository.save(heir);

            sendRemoveHeirNotification(request.getOwnerId(), request.getHeirId());

            return true;
        }

        return false;
    }

    private void sendRemoveHeirNotification(Long ownerId, Long heirId) {
        NotificationCreationRequestDto notificationCreationRequestDto = new NotificationCreationRequestDto(
                ownerId,
                heirId,
                NotificationTypesEnum.HEIR_DISINHERITANCE.toString()
        );

        restTemplate.postForObject(
                NOTIFICATION_URL,
                notificationCreationRequestDto,
                NotificationCreationRequestDto.class
        );
    }
}
