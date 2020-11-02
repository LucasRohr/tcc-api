package com.service.invite.service;

import com.service.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetInviteOwnerByIdService {

    @Autowired
    private InviteRepository inviteRepository;

    public Long getInviteOwner(Long inviteId) {
        return inviteRepository.findById(inviteId).get().getOwner().getId();
    }

}
