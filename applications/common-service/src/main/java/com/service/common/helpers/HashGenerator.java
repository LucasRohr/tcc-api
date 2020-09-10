package com.service.common.helpers;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class HashGenerator {
    public String generateHash(byte[] file) {
        return BCrypt.hashpw(new String(file), BCrypt.gensalt());
    }
}
