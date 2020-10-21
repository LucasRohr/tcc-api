package com.service.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isAlive;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    public Owner(Boolean isAlive, Account account) {
        this.isAlive = isAlive;
        this.account = account;
    }
}
