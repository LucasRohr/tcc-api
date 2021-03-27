package com.service.common.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SymmetricCrypto {

    private static final String KEY_GENERATION_ALGORITHM = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

    public static SecretKey generateKey(String password) {
        SecureRandom secureRandom = new SecureRandom(password.getBytes(StandardCharsets.UTF_8));
        KeyGenerator keygenerator = null;
        try {
            keygenerator = KeyGenerator.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keygenerator.init(256, secureRandom);
        SecretKey key = keygenerator.generateKey();
        return key;
    }

    public static byte[] createInitializationVector() {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    public byte[] encrypt(String plainText, SecretKey secretKey, byte[] initializationVector) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
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

     static String decrypt(byte[] cipherText, SecretKey secretKey, byte[] initializationVector) {
         Cipher cipher = null;
         try {
             cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (NoSuchPaddingException e) {
             e.printStackTrace();
         }

         IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

         try {
             cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
         } catch (InvalidKeyException e) {
             e.printStackTrace();
         } catch (InvalidAlgorithmParameterException e) {
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
