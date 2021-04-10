package com.service.common.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class SymmetricCrypto {

    private static final String KEY_GENERATION_ALGORITHM = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

    public static SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = factory.generateSecret(spec).getEncoded();

        SecretKeySpec keySpec = new SecretKeySpec(key, KEY_GENERATION_ALGORITHM);

        return keySpec;
    }

    private static byte[] createInitializationVector() {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);
        return initializationVector;
    }

    public static String encrypt(String plainText, SecretKey secretKey) {
        byte[] initializationVector = createInitializationVector();
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
            return new String(cipher.doFinal(plainText.getBytes()));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String decrypt(String cipherText, SecretKey secretKey) {
        byte[] initializationVector = createInitializationVector();
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
             result = cipher.doFinal(cipherText.getBytes());
         } catch (IllegalBlockSizeException e) {
             e.printStackTrace();
         } catch (BadPaddingException e) {
             e.printStackTrace();
         }

         return new String(result);
     }
}
