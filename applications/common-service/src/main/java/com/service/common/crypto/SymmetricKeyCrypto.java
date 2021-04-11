package com.service.common.crypto;

import com.service.common.domain.fabric.account.AccountAsset;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SymmetricKeyCrypto {

    private static final String KEY_GENERATION_ALGORITHM = "RSA";

    public static String encryptKey(byte[] key, List<AccountAsset> accounts) {
        final String[] encryptedKey = {""};

        accounts.forEach(account -> {
            PublicKey accountPublicKey = convertPublicKeyString(account.getPublicKey());
            String encryptedAccountKey = new String(AsymmetricCrypto.encrypt(key, accountPublicKey), StandardCharsets.ISO_8859_1);

            encryptedKey[0] = encryptedKey[0].concat(account.getAccountId() + ":::" + encryptedAccountKey + "---");
        });

        return encryptedKey[0];
    }

    public static SecretKey decryptKey(String encryptedKeyString, AccountAsset account) {
        List<String> encryptedKeyParts = Arrays.asList(encryptedKeyString.split("---"));

        String accountEncryptedKeyPart = encryptedKeyParts.stream().filter(keyPart -> {
            String accountId = Arrays.asList(keyPart.split(":::")).get(0);

            return accountId.equals(account.getAccountId().toString());
        }).collect(Collectors.joining());

        String encryptedKey = Arrays.asList(accountEncryptedKeyPart.split(":::")).get(1).replace("---", "");
        PrivateKey privateKey = convertPrivateKeyString(account.getPrivateKey());

        byte[] decodedKey = AsymmetricCrypto.decrypt(encryptedKey, privateKey);

        System.out.println("==== decodedKey ====\n\n");
        System.out.println(decodedKey);
        System.out.println("\n\n========");

        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private static PrivateKey convertPrivateKeyString(String stringKey) {
        PrivateKey privateKey = null;

        byte[] bytePrivateKey  = Base64.getDecoder().decode(stringKey);
        KeyFactory factory = null;

        try {
            factory = KeyFactory.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            privateKey = factory.generatePrivate(new PKCS8EncodedKeySpec(bytePrivateKey));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return privateKey;
    }

    private static PublicKey convertPublicKeyString(String stringKey) {
        PublicKey publicKey = null;

        byte[] bytePublicKey  = Base64.getDecoder().decode(stringKey);
        KeyFactory factory = null;

        try {
            factory = KeyFactory.getInstance(KEY_GENERATION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            publicKey = factory.generatePublic(new X509EncodedKeySpec(bytePublicKey));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return publicKey;
    }

}
