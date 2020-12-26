package com.service.invite.service;

import com.service.invite.controller.request.InviteUpdateRequest;
import com.service.invite.domain.Invite;
import com.service.invite.enums.InviteStatus;
import com.service.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateInviteService {



    @Autowired
    private InviteRepository inviteRepository;

    public void updateInvite(InviteUpdateRequest inviteUpdateRequest) {
        Invite invite = inviteRepository.findById(inviteUpdateRequest.getInviteId()).get();
        boolean hasAcceptedInvite = inviteUpdateRequest.isAccepted();

        InviteStatus inviteStatus = hasAcceptedInvite ? InviteStatus.ACCEPTED : InviteStatus.REJECTED;

        invite.setStatus(inviteStatus);
        inviteRepository.save(invite);
    }



}
