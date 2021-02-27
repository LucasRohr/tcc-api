package com.service.user.exceptions;

public class InvalidDeathCertificateException extends RuntimeException {
    public InvalidDeathCertificateException() {
        super("O código da certidão de óbito digital enviado é inválido.");
    }
}
