package com.service.invite.service;

import com.service.invite.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckInviteByCodeService {

    @Autowired
    private InviteRepository inviteRepository;

    public boolean checkInvite(String code) {
        return inviteRepository.getInviteByCode(code) != null;
    }

}
