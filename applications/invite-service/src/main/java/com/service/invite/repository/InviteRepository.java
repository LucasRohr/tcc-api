package com.service.invite.repository;


import com.service.invite.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    @Query("SELECT i FROM Invite i WHERE i.receiver.id = ?1 AND i.status = 'PENDING'")
    List<Invite> getUserInvites(Long userId);


    @Query("SELECT i FROM Invite i WHERE i.inviteCode = ?1")
    Invite getInviteByCode(String inviteCode);
}
