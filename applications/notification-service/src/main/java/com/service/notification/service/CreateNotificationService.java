package com.service.notification.service;

import com.service.common.domain.Account;
import com.service.common.repository.AccountRepository;
import com.service.common.dto.NotificationCreationRequestDto;
import com.service.notification.domain.Notification;
import com.service.common.enums.NotificationTypesEnum;
import com.service.notification.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateNotificationService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void create(NotificationCreationRequestDto notificationCreationRequestDto) {
        Account account = accountRepository.findById(notificationCreationRequestDto.getAccountId()).get();
        Account receiver = accountRepository.findById(notificationCreationRequestDto.getReceiverId()).get();

        NotificationTypesEnum type = NotificationTypesEnum.valueOf(notificationCreationRequestDto.getType());

        Notification newNotification = new Notification(
                account,
                receiver,
                type,
                LocalDateTime.now()
        );

        notificationsRepository.save(newNotification);
    }

}
