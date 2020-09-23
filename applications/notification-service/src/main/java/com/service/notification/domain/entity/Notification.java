package com.service.notification.domain.entity;

import com.service.common.domain.Account;
import com.service.notification.enums.NotificationTypesEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private NotificationTypesEnum type;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private Account account;
}
