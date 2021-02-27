package com.service.user.service.account;

import com.service.common.domain.Heir;
import com.service.common.dto.NotificationCreationRequestDto;
import com.service.common.enums.HeirStatusEnum;
import com.service.common.enums.NotificationTypesEnum;
import com.service.common.repository.HeirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ActivateHeirsHeritagesServices {

    private static final String NOTIFICATION_URL =
            "http://notification-service/notification-creation";

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void activateHeirs(Long ownerId) {
        List<Heir> heirs = heirRepository.getOwnerHeirs(ownerId);

        heirs.forEach(heir -> {
            heir.setStatus(HeirStatusEnum.ACTIVE);
            heirRepository.save(heir);

            NotificationCreationRequestDto notificationCreationRequestDto = new NotificationCreationRequestDto(
                    ownerId,
                    heir.getAccount().getId(),
                    NotificationTypesEnum.ACTIVATED_HERITAGE.toString()
            );

            restTemplate.postForObject(
                    NOTIFICATION_URL,
                    notificationCreationRequestDto,
                    NotificationCreationRequestDto.class
            );
        });
    }

}
