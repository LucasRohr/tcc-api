package com.service.common.crypto;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.helpers.KeysConverter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SymmetricKeyCrypto {
    private static final String keyArn = "arn:aws:kms:sa-east-1:665638235375:key/c252ed25-547c-49e0-99cd-816f44fa9726";

    private static final AwsCrypto awsCrypto = AwsCrypto.standard();

    public static String encryptKey(byte[] key, List<AccountAsset> accounts) {
        final String[] encryptedKey = {""};
        KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn);

        accounts.forEach(account -> {
            byte[] base64EncodedPublicKey = Base64.getDecoder().decode(account.getPublicKey());

            CryptoResult<byte[], KmsMasterKey> decryptedPublicKeyResult = awsCrypto.decryptData(keyProvider, base64EncodedPublicKey);

            String decryptedPublicKeyString = Base64.getEncoder().encodeToString(decryptedPublicKeyResult.getResult());

            PublicKey accountPublicKey = KeysConverter.convertPublicKeyString(decryptedPublicKeyString);
            String encryptedAccountKey = Base64.getEncoder().encodeToString(AsymmetricCrypto.encrypt(key, accountPublicKey));

            encryptedKey[0] = encryptedKey[0].concat(account.getAccountId().toString() + ":::" + encryptedAccountKey + "---");
        });

        return encryptedKey[0];
    }

    public static SecretKey decryptKey(String encryptedKeyString, AccountAsset account) {
        List<String> encryptedKeyParts = Arrays.asList(encryptedKeyString.split("---"));
        KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn);

        String accountEncryptedKeyPart = encryptedKeyParts.stream().filter(keyPart -> {
            String accountId = Arrays.asList(keyPart.split(":::")).get(0);

            return accountId.equals(account.getAccountId().toString());
        }).collect(Collectors.joining());

        String encryptedBase64Key = Arrays.asList(accountEncryptedKeyPart.split(":::")).get(1).replace("---", "");
        byte[] encoded64Key = Base64.getDecoder().decode(encryptedBase64Key);

        byte[] base64EncodedPrivateKey = Base64.getDecoder().decode(account.getPrivateKey());
        CryptoResult<byte[], KmsMasterKey> decryptedPrivateKeyResult = awsCrypto.decryptData(keyProvider, base64EncodedPrivateKey);

        String decryptedPrivateKeyString = Base64.getEncoder().encodeToString(decryptedPrivateKeyResult.getResult());

        PrivateKey privateKey = KeysConverter.convertPrivateKeyString(decryptedPrivateKeyString);
        byte[] decodedKey = AsymmetricCrypto.decrypt(encoded64Key, privateKey);

        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

}
