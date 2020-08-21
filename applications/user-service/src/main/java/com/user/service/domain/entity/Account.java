package com.user.service.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
