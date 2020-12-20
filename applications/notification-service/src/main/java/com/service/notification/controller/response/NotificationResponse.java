package com.service.notification.controller.response;

import com.service.common.enums.NotificationTypesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long id;

    private String message;

    private boolean isRead;

    private NotificationTypesEnum type;

}
