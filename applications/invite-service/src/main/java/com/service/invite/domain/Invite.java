package com.service.invite.domain;

import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.common.domain.User;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InviteStatus status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column()
    private String inviteCode;

    public Invite(Owner owner, InviteStatus status) {
        this.owner = owner;
        this.status = status;
    }

    public Invite(Owner owner, User receiver, InviteStatus status) {
        this.owner = owner;
        this.receiver = receiver;
        this.status = status;
    }
}
