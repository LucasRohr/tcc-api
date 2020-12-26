package com.service.notification.service;

import com.service.common.domain.Account;
import com.service.common.repository.AccountRepository;
import com.service.notification.controller.response.NotificationResponse;
import com.service.notification.domain.Notification;
import com.service.common.enums.NotificationTypesEnum;
import com.service.notification.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAccountNotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<NotificationResponse> getNotifications(Long receiverId) {
        List<Notification> notifications = notificationsRepository.getNotificationsByReceiverId(receiverId);
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        notifications.forEach(notification -> {
            NotificationTypesEnum type = notification.getType();
            boolean hasMessageConcat = type.isHasConcat();
            String notificationMessage = "";

            if(hasMessageConcat) {
                Account notificationAccount = accountRepository.findById(notification.getAccount().getId()).get();
                notificationMessage = type.getMessage(notificationAccount.getUser().getName());
            } else {
                notificationMessage = type.getMessage("");
            }

            NotificationResponse notificationResponse = new NotificationResponse(
                    notification.getId(),
                    notificationMessage,
                    notification.getIsRead(),
                    notification.getType()
            );

            notificationResponses.add(notificationResponse);
        });

        return notificationResponses;
    }

}
