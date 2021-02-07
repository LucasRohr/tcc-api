package com.service.invite.controller;

import com.service.invite.controller.request.InviteCodeUpdateRequest;
import com.service.invite.controller.request.InviteHeirRequest;
import com.service.invite.controller.request.InviteUpdateRequest;
import com.service.invite.controller.response.UserInviteResponse;
import com.service.invite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invites")
public class InviteController {

    @Autowired
    private InviteHeirService inviteHeirEmailService;

    @Autowired
    private GetUserInvitesService getUserInvitesService;

    @Autowired
    private UpdateInviteService updateInviteService;

    @Autowired
    private UpdateInviteCodeService updateInviteCodeService;

    @Autowired
    private CheckInviteByCodeService checkInviteByCodeService;

    @Autowired
    private GetInviteOwnerByIdService getInviteOwnerByIdService;

    @PostMapping("heir-invite")
    public void inviteHeir(@RequestBody InviteHeirRequest inviteHeirRequest) {
        inviteHeirEmailService.inviteByEmail(inviteHeirRequest);
    }

    @PutMapping("invite-response")
    public void updateInvite(@RequestBody InviteUpdateRequest inviteUpdateRequest) {
        updateInviteService.updateInvite(inviteUpdateRequest);
    }

    @GetMapping("user-invites")
    public List<UserInviteResponse> getUserInvites(@RequestParam("user_id") Long userId) {
        return getUserInvitesService.getInvites(userId);
    }

    @PutMapping("invite-code-update")
    public void updateInvite(@RequestBody InviteCodeUpdateRequest inviteCodeUpdateRequest) {
        updateInviteCodeService.updateCode(inviteCodeUpdateRequest);
    }

    @GetMapping("invite-check")
    public boolean checkInviteByCode(@RequestParam("code") String code) {
        return checkInviteByCodeService.checkInvite(code);
    }

    @GetMapping("invite-by-id")
    public Long getInviteById(@RequestParam("invite_id") Long inviteId) {
        return getInviteOwnerByIdService.getInviteOwner(inviteId);
    }
}
