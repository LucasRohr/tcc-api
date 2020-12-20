package com.service.notification.domain;

import com.service.common.domain.Account;
import com.service.common.enums.NotificationTypesEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationTypesEnum type;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isRead = false;

    @ManyToOne
    @JoinColumn(nullable = false, name="receiver_id")
    private Account receiver;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    public Notification(
            Account account,
            Account receiver,
            NotificationTypesEnum type,
            LocalDateTime createdAt
    ) {
        this.account = account;
        this.receiver = receiver;
        this.type = type;
        this.createdAt = createdAt;
    }

}
