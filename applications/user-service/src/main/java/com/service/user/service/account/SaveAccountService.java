package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.fabric.account.AccountRecordModel;
import com.service.common.helpers.CryptoUtils;
import com.service.common.repository.AccountRepository;
import com.service.common.service.fabric.account.SaveAccountAssetService;
import com.service.common.enums.AccountTypes;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

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

    public void saveAccount(Account account, AccountTypes accountType, Long ownerId)
            throws NoSuchAlgorithmException, ProposalException, InvalidArgumentException {
        Account savedAccount = accountRepository.save(account);

        KeyPair accountKeys = CryptoUtils.generateKeyPair();

        if(accountType == AccountTypes.OWNER) {
            saveOwnerService.saveOwner(savedAccount, accountKeys);
        } else {
            saveHeirService.saveHeir(savedAccount, accountKeys, ownerId);
        }

        AccountRecordModel accountRecord = new AccountRecordModel(
                savedAccount.getId(),
                accountKeys.getPrivate().toString(),
                accountKeys.getPublic().toString(),
                accountType.toString()
        );

        saveAccountAssetService.createTransaction(accountRecord);
    }
}
