package com.service.common.exceptions;

public class BadRequestException extends Exception {

    public BadRequestException(Throwable throwable) {
        super("Erro, verifique os campos enviados e tente novamente.", throwable);
    }

}
