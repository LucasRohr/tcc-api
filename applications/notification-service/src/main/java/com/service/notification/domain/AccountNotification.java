package com.service.notification.domain;

import com.service.common.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accounts_notifications")
public class AccountNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(nullable = false, name="receiver_id")
    private Account receiver;

    @ManyToOne
    @JoinColumn(nullable = false, name="notification_id")
    private Notification notification;
}
