package com.service.message.service.email;

import com.service.common.dto.UpdateUserLoginTokenRequestDto;
import com.service.common.helpers.RandomCode;
import com.service.message.controller.requests.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SendLoginAuthEmailService {
    private static final int codeLength = 6;
    private static final String UPDATE_TOKEN_URL = "http://user-service/users/login-token-update";

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine htmlTemplateEngine;

    @Autowired
    private RestTemplate restTemplate;

    public void sendEmail(EmailRequest emailRequest) throws MessagingException, IOException {
        Context ctx = prepareContext(emailRequest);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper message = prepareMessage(mimeMessage, emailRequest);

        String htmlContent = this.htmlTemplateEngine.process(emailRequest.getType().getTemplateName(), ctx);
        message.setText(htmlContent, true);


        this.mailSender.send(mimeMessage);
        this.htmlTemplateEngine.clearTemplateCache();

        UpdateUserLoginTokenRequestDto updateUserLoginTokenRequestDto = new UpdateUserLoginTokenRequestDto(
                        emailRequest.getEmail(),
                        ctx.getVariable("authToken").toString()
                );

        restTemplate.postForObject(
                UPDATE_TOKEN_URL,
                updateUserLoginTokenRequestDto,
                UpdateUserLoginTokenRequestDto.class
        );
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
