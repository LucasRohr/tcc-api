package com.service.notification.service;

import com.service.notification.domain.Notification;
import com.service.notification.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadNotificationService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    public void readNotification(Long notificationId) {
        Notification notification = notificationsRepository.findById(notificationId).get();

        notification.setIsRead(true);

        notificationsRepository.save(notification);
    }

}
