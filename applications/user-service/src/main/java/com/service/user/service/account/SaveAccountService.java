package com.service.user.service.account;

import com.service.common.crypto.AsymmetricCrypto;
import com.service.common.domain.Account;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.account.AccountRecordModel;
import com.service.common.helpers.KeysConverter;
import com.service.common.repository.AccountRepository;
import com.service.common.service.account.GetAccountAssetByIdCommonService;
import com.service.common.service.fabric.account.SaveAccountAssetService;
import com.service.common.enums.AccountTypes;
import com.service.user.config.PasswordEncoder;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

@Service
public class SaveAccountService {
    private static final long MASTER_KEY_ID = 0;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SaveOwnerService saveOwnerService;

    @Autowired
    private SaveHeirService saveHeirService;

    @Autowired
    private SaveAccountAssetService saveAccountAssetService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private GetAccountAssetByIdCommonService getAccountAssetByIdCommonService;

    public void saveAccount(Account account, AccountTypes accountType, Long ownerId, String cryptoPassword)
            throws ProposalException, InvalidArgumentException {
        Account savedAccount = accountRepository.save(account);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());

        if(accountType == AccountTypes.OWNER) {
            saveOwnerService.saveOwner(savedAccount);
        } else {
            saveHeirService.saveHeir(savedAccount, ownerId);
        }

        AccountAsset checkedAsset = this.checkMasterKeyCreation(zonedDateTime);

        if (checkedAsset == null) {
            setTimeout(() -> {
                createAccountAsset(savedAccount, accountType, zonedDateTime, cryptoPassword);
            }, 5000);
        } else {
            createAccountAsset(savedAccount, accountType, zonedDateTime, cryptoPassword);
        }
    }

    private void createAccountAsset(Account savedAccount, AccountTypes accountType, ZonedDateTime zonedDateTime, String cryptoPassword) {
        Long timestamp = zonedDateTime.toInstant().toEpochMilli();

        KeyPair accountKeys = AsymmetricCrypto.generateKeyPair(cryptoPassword);

        AccountAsset masterAsset = getAccountAssetByIdCommonService.getAccount(MASTER_KEY_ID);

        PublicKey masterPublicKey = KeysConverter.convertPublicKeyString(masterAsset.getPublicKey());

        byte[] bytePublicKey = AsymmetricCrypto.encrypt(accountKeys.getPublic().getEncoded(), masterPublicKey);
        String stringPublicKey = Base64.getEncoder().encodeToString(bytePublicKey);

        byte[] bytePrivateKey = AsymmetricCrypto.encrypt(accountKeys.getPrivate().getEncoded(), masterPublicKey);
        String stringPrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);

        AccountRecordModel accountRecord = new AccountRecordModel(
                savedAccount.getId(),
                stringPrivateKey,
                stringPublicKey,
                accountType.toString(),
                encoder.bCryptPasswordEncoder().encode(cryptoPassword),
                timestamp
        );

        try {
            saveAccountAssetService.createTransaction(accountRecord);
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    private AccountAsset checkMasterKeyCreation(ZonedDateTime zonedDateTime)
            throws InvalidArgumentException, ProposalException {
        AccountAsset accountAsset = getAccountAssetByIdCommonService.getAccount(MASTER_KEY_ID);

        KeyPair accountKeys = AsymmetricCrypto.generateKeyPair("");
        Long timestamp = zonedDateTime.toInstant().toEpochMilli();

        byte[] bytePublicKey = accountKeys.getPublic().getEncoded();
        String stringPublicKey = Base64.getEncoder().encodeToString(bytePublicKey);

        byte[] bytePrivateKey = accountKeys.getPrivate().getEncoded();
        String stringPrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);

        if (accountAsset == null) {
            AccountRecordModel accountRecord = new AccountRecordModel(
                    MASTER_KEY_ID,
                    stringPrivateKey,
                    stringPublicKey,
                    "OWNER",
                    "",
                    timestamp
            );

            saveAccountAssetService.createTransaction(accountRecord);
        }

        return accountAsset;
    }

    private static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

}
