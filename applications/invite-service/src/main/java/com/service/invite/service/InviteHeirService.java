package com.service.invite.service;

import com.service.common.domain.Account;
import com.service.common.domain.Owner;
import com.service.common.domain.User;
import com.service.common.dto.NotificationCreationRequestDto;
import com.service.common.enums.NotificationTypesEnum;
import com.service.common.repository.AccountRepository;
import com.service.common.repository.OwnerRepository;
import com.service.invite.controller.request.InviteHeirRequest;
import com.service.invite.domain.Invite;
import com.service.invite.dto.EmailInviteDto;
import com.service.invite.dto.SmsInviteDto;
import com.service.invite.enums.InviteStatus;
import com.service.invite.repository.InviteRepository;
import com.service.user.service.GetUserByEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InviteHeirService {

    private static final String INVITE_MESSAGE_KEY = "HEIR_INVITE";
    private static final String INVITE_EMAIL_MESSAGE_URL = "https://message-service/messages/email/heir-invite";
    private static final String INVITE_SMS_MESSAGE_URL = "https://message-service/messages/sms";
    private static final String NOTIFICATION_URL =
            "https://notification-service/notification-creation";

    @Autowired
    private GetUserByEmailService getUserByEmailService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void inviteByEmail(InviteHeirRequest inviteHeirRequest) {
        User invitedUser = getUserByEmailService.getUserByEmail(inviteHeirRequest.getEmail());
        Owner inviteOwner = ownerRepository.findByAccountId(inviteHeirRequest.getOwnerId());

        Long createdInviteId = null;

        if(invitedUser == null) {
            Invite invite = new Invite(inviteOwner, InviteStatus.PENDING);
            Invite createdInvite = inviteRepository.save(invite);

            createdInviteId = createdInvite.getId();
        } else {
            Invite invite = new Invite(inviteOwner, invitedUser, InviteStatus.PENDING);
            Invite createdInvite = inviteRepository.save(invite);

            createdInviteId = createdInvite.getId();

            sendInviteToHeirAccounts(invitedUser.getId(), inviteOwner.getId());
        }

        if(!inviteHeirRequest.getEmail().isEmpty()) {
            EmailInviteDto emailInviteDto = new EmailInviteDto(
                    inviteHeirRequest.getEmail(),
                    INVITE_MESSAGE_KEY,
                    inviteHeirRequest.getOwnerName(),
                    invitedUser != null,
                    createdInviteId
            );

            restTemplate.postForObject(INVITE_EMAIL_MESSAGE_URL, emailInviteDto, EmailInviteDto.class);
        }

        if(!inviteHeirRequest.getPhone().isEmpty()) {
            SmsInviteDto smsInviteDto = new SmsInviteDto(
                    inviteHeirRequest.getPhone(),
                    inviteHeirRequest.getOwnerName(),
                    invitedUser != null,
                    createdInviteId
            );

            restTemplate.postForObject(INVITE_SMS_MESSAGE_URL, smsInviteDto, SmsInviteDto.class);
        }
    }

    private void sendInviteToHeirAccounts(Long userId, Long ownerId) {
        List<Account> heirsAccounts = accountRepository.getAllUserAccounts(userId);

        heirsAccounts.forEach(account -> {
            NotificationCreationRequestDto notificationCreationRequestDto = new NotificationCreationRequestDto(
                    ownerId,
                    account.getId(),
                    NotificationTypesEnum.HEIR_INVITE.toString()
            );

            restTemplate.postForObject(
                    NOTIFICATION_URL,
                    notificationCreationRequestDto,
                    NotificationCreationRequestDto.class
            );
        });
    }

}
