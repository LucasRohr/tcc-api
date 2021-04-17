package com.service.common.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class AsymmetricCrypto {

    private static final String KEY_GENERATION_ALGORITHM = "RSA";

    public static KeyPair generateKeyPair(String salt) {
        SecureRandom random = new SecureRandom(salt.getBytes());
        KeyPairGenerator keyPairGenerator = null;

        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyPairGenerator.initialize(4096, random);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encrypt(byte[] cryptContent, PublicKey publicKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.PUBLIC_KEY, publicKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            return cipher.doFinal(cryptContent);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public static byte[] decrypt(byte[] cryptContent, PrivateKey privateKey) {

        Cipher cipher  = null;
        try {
            cipher = Cipher.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.PRIVATE_KEY, privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] result = new byte[0];
        try {
            result = cipher.doFinal(cryptContent);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
