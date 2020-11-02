package com.service.message.component.link_code;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@Component
@Data
@AllArgsConstructor
public class RandomCode {

    private static final int linkLength = 6;

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String digits = "0123456789";

    public static final String alphanumeric = upper + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] bufferer;

    public RandomCode(int length, Random random, String symbols) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }

        if(symbols.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.bufferer = new char[length];
    }

    public RandomCode(int length, Random random) {
        this(length, random, alphanumeric);
    }


    public RandomCode() {
        this(linkLength, new SecureRandom());
    }

    public RandomCode(int length) {
        this(length, new SecureRandom());
    }

    public String nextString() {
        for (int idx = 0; idx < this.bufferer.length; ++idx)
            this.bufferer[idx] = symbols[random.nextInt(symbols.length)];
        return new String(this.bufferer);
    }

}
