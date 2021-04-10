package com.service.user.service.account;

import com.service.common.crypto.AsymmetricCrypto;
import com.service.common.domain.Account;
import com.service.common.domain.fabric.account.AccountRecordModel;
import com.service.common.repository.AccountRepository;
import com.service.common.service.fabric.account.SaveAccountAssetService;
import com.service.common.enums.AccountTypes;
import com.service.user.config.PasswordEncoder;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;

@Service
public class SaveAccountService {
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

    public void saveAccount(Account account, AccountTypes accountType, Long ownerId, String cryptoPassword)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
        Account savedAccount = accountRepository.save(account);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        Long timestamp = zonedDateTime.toInstant().toEpochMilli();

        KeyPair accountKeys = AsymmetricCrypto.generateKeyPair(cryptoPassword);

        if(accountType == AccountTypes.OWNER) {
            saveOwnerService.saveOwner(savedAccount);
        } else {
            saveHeirService.saveHeir(savedAccount, ownerId);
        }

        byte[] bytePublicKey = accountKeys.getPublic().getEncoded();
        String stringPublicKey = Base64.getEncoder().encodeToString(bytePublicKey);

        byte[] bytePrivateKey = accountKeys.getPrivate().getEncoded();
        String stringPrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);

        AccountRecordModel accountRecord = new AccountRecordModel(
                savedAccount.getId(),
                stringPrivateKey,
                stringPublicKey,
                accountType.toString(),
                encoder.bCryptPasswordEncoder().encode(cryptoPassword),
                timestamp
        );

        saveAccountAssetService.createTransaction(accountRecord);
    }
}
