package com.service.user.exceptions;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException() {
        super("A nova senha deve ser diferente da anterior.");
    }
}
