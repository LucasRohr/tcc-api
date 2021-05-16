package com.service.common.helpers;

import com.service.common.exceptions.CryptoException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String keyValue = "crypto_key_value";

    public static void encrypt(SecretKey secretKey, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, secretKey, inputFile, outputFile);
    }

    public static void decrypt(SecretKey secretKey, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, secretKey, inputFile, outputFile);
    }

    private static void doCrypto(
            int cipherMode, SecretKey secretKey, File inputFile,
            File outputFile) throws CryptoException {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey, new IvParameterSpec(new byte[16]));

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
        } catch (
                NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException | IOException | InvalidAlgorithmParameterException ex
        ) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static String encryptSimpleString(String text) {
        String encodedText = "";

        try {
            Key key = generateKey();

            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);

            byte[] encVal = c.doFinal(text.getBytes());
            encodedText = Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodedText;
    }

    public static String decryptSimpleString(String encryptedString) {
        String decodedText = "";

        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            byte[] decodedValue = Base64.getDecoder().decode(encryptedString);
            byte[] decValue = c.doFinal(decodedValue);

            decodedText = new String(decValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decodedText;
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue.getBytes(), ALGORITHM);
    }
}
