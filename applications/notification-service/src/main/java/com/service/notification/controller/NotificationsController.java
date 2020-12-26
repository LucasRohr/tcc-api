package com.service.notification.controller;

import com.service.common.dto.NotificationCreationRequestDto;
import com.service.notification.controller.response.NotificationResponse;
import com.service.notification.service.CreateNotificationService;
import com.service.notification.service.GetAccountNotificationsService;
import com.service.notification.service.ReadNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("")
public class NotificationsController {

    @Autowired
    private CreateNotificationService createNotificationService;

    @Autowired
    private ReadNotificationService readNotificationService;

    @Autowired
    private GetAccountNotificationsService getAccountNotificationsService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("notification-creation")
    public void createNotification(@RequestBody @Valid NotificationCreationRequestDto notificationCreationRequestDto) {
        createNotificationService.create(notificationCreationRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("notification-read")
    public void createNotification(@RequestParam("notification_id") Long notificationId) {
        readNotificationService.readNotification(notificationId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("account-notifications")
    public List<NotificationResponse> getAccountNotifications(@RequestParam("account_id") Long receiverId) {
        return getAccountNotificationsService.getNotifications(receiverId);
    }

}
