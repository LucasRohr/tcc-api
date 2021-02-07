package com.service.common.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(Throwable throwable) {
        super("Não foi possível encontrar a informação solicitada.", throwable);
    }

}
