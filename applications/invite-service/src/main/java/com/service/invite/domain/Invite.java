package com.service.invite.domain;

import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.invite.enums.InviteStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "invites")
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private InviteStatus status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(nullable = false, name = "receiver_id")
    private Heir receiver;
}
