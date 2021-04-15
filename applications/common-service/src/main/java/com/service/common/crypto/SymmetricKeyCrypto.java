package com.service.common.crypto;

import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.helpers.KeysConverter;
import com.service.common.service.account.GetAccountAssetByIdCommonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SymmetricKeyCrypto {
    private static final long MASTER_KEY_ID = 0;

    @Autowired
    private static GetAccountAssetByIdCommonService getAccountAssetByIdCommonService;

    private static AccountAsset masterAsset = getAccountAssetByIdCommonService.getAccount(MASTER_KEY_ID);
    private static PrivateKey masterPrivateKey = KeysConverter.convertPrivateKeyString(masterAsset.getPrivateKey());

    public static String encryptKey(byte[] key, List<AccountAsset> accounts) {
        final String[] encryptedKey = {""};

        accounts.forEach(account -> {
            byte[] byte64AccountKey = Base64.getDecoder().decode(account.getPublicKey());
            byte[] decodedPublicKeyBytes = AsymmetricCrypto.decrypt(byte64AccountKey, masterPrivateKey);
            String stringAccountPublicKey = Base64.getEncoder().encodeToString(decodedPublicKeyBytes);

            PublicKey accountPublicKey = KeysConverter.convertPublicKeyString(stringAccountPublicKey);
            String encryptedAccountKey = Base64.getEncoder().encodeToString(AsymmetricCrypto.encrypt(key, accountPublicKey));

            encryptedKey[0] = encryptedKey[0].concat(account.getAccountId().toString() + ":::" + encryptedAccountKey + "---");
        });

        return encryptedKey[0];
    }

    public static SecretKey decryptKey(String encryptedKeyString, AccountAsset account) {
        List<String> encryptedKeyParts = Arrays.asList(encryptedKeyString.split("---"));

        String accountEncryptedKeyPart = encryptedKeyParts.stream().filter(keyPart -> {
            String accountId = Arrays.asList(keyPart.split(":::")).get(0);

            return accountId.equals(account.getAccountId().toString());
        }).collect(Collectors.joining());

        String encryptedBase64Key = Arrays.asList(accountEncryptedKeyPart.split(":::")).get(1).replace("---", "");
        byte[] encoded64Key = Base64.getDecoder().decode(encryptedBase64Key);

        byte[] byte64AccountKey = Base64.getDecoder().decode(account.getPrivateKey());
        byte[] decodedPrivateKeyBytes = AsymmetricCrypto.decrypt(byte64AccountKey, masterPrivateKey);
        String stringAccountPrivateKey = Base64.getEncoder().encodeToString(decodedPrivateKeyBytes);

        PrivateKey privateKey = KeysConverter.convertPrivateKeyString(stringAccountPrivateKey);
        byte[] decodedKey = AsymmetricCrypto.decrypt(encoded64Key, privateKey);

        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

}
