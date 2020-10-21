package com.service.invite.service;

import com.service.invite.controller.response.UserInviteResponse;
import com.service.invite.domain.Invite;
import com.service.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetUserInvitesService {

    @Autowired
    private InviteRepository inviteRepository;

    public List<UserInviteResponse> getInvites(Long userId) {
        List<Invite> invites = inviteRepository.getUserInvites(userId);

        List<UserInviteResponse> userInviteResponses = new ArrayList<>();

        invites.forEach(invite -> {
            UserInviteResponse userInviteResponse = new UserInviteResponse(
                    invite.getId(),
                    invite.getOwner().getAccount().getName(),
                    invite.getOwner().getId()
            );

            userInviteResponses.add(userInviteResponse);
        });

        return userInviteResponses;
    }

}
