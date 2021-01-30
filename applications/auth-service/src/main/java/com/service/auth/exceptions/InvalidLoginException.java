package com.service.auth.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Dados de login inválidos, tente novamente");
    }
}
