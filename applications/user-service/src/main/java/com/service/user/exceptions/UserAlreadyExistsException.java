package com.service.user.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Já existe um usuário com este mesmo e-mail cadastrado.");
    }
}
