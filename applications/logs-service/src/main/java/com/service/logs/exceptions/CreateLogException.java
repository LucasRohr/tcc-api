package com.service.logs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CreateLogException extends RuntimeException {

    public CreateLogException() {
        super("Não foi possível criar o log.");
    }

}
