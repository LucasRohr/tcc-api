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
        SecureRandom random = new SecureRandom(salt.getBytes(StandardCharsets.UTF_8));
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(2048, random);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encrypt(String plainText, PublicKey publicKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            return cipher.doFinal(plainText.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public static String decrypt(byte[] cipherText, PrivateKey privateKey) {

        Cipher cipher  = null;
        try {
            cipher = Cipher.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] result = new byte[0];
        try {
            result = cipher.doFinal(cipherText);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(result);
    }
}
