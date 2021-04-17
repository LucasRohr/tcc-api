package com.service.common.helpers;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeysConverter {

    private static final String KEY_GENERATION_ALGORITHM = "RSA";

    public static PrivateKey convertPrivateKeyString(String stringKey) {
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

    public static PublicKey convertPublicKeyString(String stringKey) {
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
