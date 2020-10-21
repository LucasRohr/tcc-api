package com.service.invite.service;

import com.service.invite.controller.request.InviteCodeUpdateRequest;
import com.service.invite.domain.Invite;
import com.service.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateInviteCodeService {

    @Autowired
    private InviteRepository inviteRepository;

    public void updateCode(InviteCodeUpdateRequest inviteCodeUpdateRequest) {
        Invite invite = inviteRepository.findById(inviteCodeUpdateRequest.getId()).get();

        invite.setInviteCode(inviteCodeUpdateRequest.getCode());

        inviteRepository.save(invite);
    }

}
