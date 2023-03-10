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
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), KEY_GENERATION_ALGORITHM);

        return secretKey;
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

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        try {
            return new String(cipher.doFinal(plainText.getBytes()), StandardCharsets.ISO_8859_1);
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
             cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
         } catch (InvalidKeyException e) {
             e.printStackTrace();
         } catch (InvalidAlgorithmParameterException e) {
             e.printStackTrace();
         }

         byte[] result = new byte[0];

         try {
             result = cipher.doFinal(cipherText.getBytes(StandardCharsets.ISO_8859_1));
         } catch (IllegalBlockSizeException e) {
             e.printStackTrace();
         } catch (BadPaddingException e) {
             e.printStackTrace();
         }

         return new String(result);
     }
}
