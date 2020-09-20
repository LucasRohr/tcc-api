package com.service.message.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SmsException extends RuntimeException {

    public SmsException() {
        super("Não foi possível enviar o SMS de convite, tente novamente.");
    }

}
