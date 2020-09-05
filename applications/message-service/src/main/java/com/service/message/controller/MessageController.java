package com.service.message.controller;

import com.service.message.controller.requests.EmailRequest;
import com.service.message.controller.requests.HeirInviteEmailRequest;
import com.service.message.controller.requests.SmsRequest;
import com.service.message.exceptions.SmsException;
import com.service.message.service.email.SendHeirInviteEmailService;
import com.service.message.service.email.SendLoginAuthEmailService;
import com.service.message.service.sms.SendSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@RestController()
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private SendHeirInviteEmailService sendHeirInviteEmailService;

    @Autowired
    private SendLoginAuthEmailService sendLoginAuthEmailService;

    @Autowired
    private SendSmsService sendSmsService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("email/heir-invite")
    public void sendEmail(@RequestBody @Valid HeirInviteEmailRequest heirInviteEmailRequest) throws MessagingException, IOException {
        sendHeirInviteEmailService.sendEmail(heirInviteEmailRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("email/login-auth")
    public void sendEmail(@RequestBody @Valid EmailRequest emailRequest) throws MessagingException, IOException {
        sendLoginAuthEmailService.sendEmail(emailRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("sms")
    public void sendSms(@RequestBody @Valid SmsRequest smsRequest) throws SmsException, IOException {
        sendSmsService.sendSms(smsRequest);
    }

}
