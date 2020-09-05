package com.service.message.service.email;

import com.service.message.component.link_code.RandomCode;
import com.service.message.controller.requests.EmailRequest;
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
public class SendLoginAuthEmailService {
    private static final int codeLength = 6;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine htmlTemplateEngine;

    public void sendEmail(EmailRequest emailRequest) throws MessagingException, IOException {
        Context ctx = prepareContext(emailRequest);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        MimeMessageHelper message = prepareMessage(mimeMessage, emailRequest);

        String htmlContent = this.htmlTemplateEngine.process(emailRequest.getType().getTemplateName(), ctx);
        message.setText(htmlContent, true);

        System.out.println(mimeMessage);

        this.mailSender.send(mimeMessage);

        this.htmlTemplateEngine.clearTemplateCache();
    }

    private Context prepareContext(EmailRequest emailRequest) {
        Context ctx = new Context();
        RandomCode randomCode = new RandomCode(codeLength);
        String authToken = randomCode.nextString();

        ctx.setVariable("authToken", authToken);

        return ctx;
    }

    private MimeMessageHelper prepareMessage(MimeMessage mimeMessage, EmailRequest emailRequest)
            throws MessagingException, IOException {

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                "UTF-8");
        message.setSubject(emailRequest.getEmail());
        message.setFrom("lucasrc17@live.com");
        message.setTo(emailRequest.getEmail());

        return message;

    }

}
