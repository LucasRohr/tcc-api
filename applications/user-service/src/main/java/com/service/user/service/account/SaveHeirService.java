package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.common.dto.NotificationCreationRequestDto;
import com.service.common.enums.HeirStatusEnum;
import com.service.common.enums.NotificationTypesEnum;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class SaveHeirService {

    private static final String NOTIFICATION_URL =
            "http://notification-service/notification-creation";

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void saveHeir(Account account, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).get();

        Heir heir = new Heir(
                HeirStatusEnum.ACCEPTED,
                owner,
                account,
                new ArrayList<>()
        );

        Heir savedHeir = heirRepository.save(heir);

        sendAcceptedInviteNotification(savedHeir.getAccount().getId(), owner.getAccount().getId());
    }

    private void sendAcceptedInviteNotification(Long accountId, Long receiverId) {
            NotificationCreationRequestDto notificationCreationRequestDto = new NotificationCreationRequestDto(
                    accountId,
                    receiverId,
                    NotificationTypesEnum.ACCEPTED_HEIR_INVITE.toString()
            );

            restTemplate.postForObject(
                    NOTIFICATION_URL,
                    notificationCreationRequestDto,
                    NotificationCreationRequestDto.class
            );
    }

}
