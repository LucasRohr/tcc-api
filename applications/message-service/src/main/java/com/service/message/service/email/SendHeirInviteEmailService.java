package com.service.message.service.email;

import com.service.message.component.link_code.RandomCode;
import com.service.message.controller.requests.HeirInviteEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SendHeirInviteEmailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine htmlTemplateEngine;

    public HeirInviteEmailRequest sendEmail(HeirInviteEmailRequest heirInviteEmailRequest) throws MessagingException, IOException {
        Context ctx = prepareContext(heirInviteEmailRequest);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        MimeMessageHelper message = prepareMessage(mimeMessage, heirInviteEmailRequest);

        String htmlContent = this.htmlTemplateEngine.process(heirInviteEmailRequest.getType().getTemplateName(), ctx);
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);

        this.htmlTemplateEngine.clearTemplateCache();

        return heirInviteEmailRequest;
    }

    private Context prepareContext(HeirInviteEmailRequest heirInviteEmailRequest) {
        Context ctx = new Context();
        RandomCode randomCode = new RandomCode();
        String linkCode = randomCode.nextString();

        ctx.setVariable("ownerName", heirInviteEmailRequest.getOwnerName());
        ctx.setVariable("linkCode", linkCode);

        return ctx;
    }

    private MimeMessageHelper prepareMessage(MimeMessage mimeMessage, HeirInviteEmailRequest heirInviteEmailRequest)
            throws MessagingException, IOException {

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                "UTF-8");
        message.setSubject(heirInviteEmailRequest.getOwnerName());
        message.setFrom("lucasrc17@live.com");
        message.setTo(heirInviteEmailRequest.getEmail());

        return message;

    }

}
