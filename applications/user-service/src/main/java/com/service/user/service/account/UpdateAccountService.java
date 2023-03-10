package com.service.user.service.account;

import com.service.common.domain.Account;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.account.AccountRecordModel;
import com.service.common.repository.AccountRepository;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.common.service.fabric.account.SaveAccountAssetService;
import com.service.user.controller.request.AccountUpdateRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class UpdateAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SaveAccountAssetService saveAccountAssetService;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    public void update(AccountUpdateRequest accountUpdateRequest) throws InvalidArgumentException, ProposalException, IOException {
        Account account = accountRepository.findById(accountUpdateRequest.getAccountId()).get();

        account.setName(accountUpdateRequest.getAccountName());
        account.setUpdatedAt(LocalDateTime.now());

        if (accountUpdateRequest.getCryptoPassword().length() != 0) {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
            Long timestamp = zonedDateTime.toInstant().toEpochMilli();

            AccountAsset currentAccountState =
                    getAccountAssetByIdService.getUserAssetById(accountUpdateRequest.getAccountId());

            boolean isSamePassword =
                    bCryptEncoder.matches(accountUpdateRequest.getCryptoPassword(), currentAccountState.getCryptoPassword());

            if (!isSamePassword) {
                throw new Error("A senha inserida n??o ?? a atual");
            }

            accountRepository.save(account);

            AccountRecordModel accountRecord = new AccountRecordModel(
                    currentAccountState.getAccountId(),
                    currentAccountState.getPrivateKey(),
                    currentAccountState.getPublicKey(),
                    currentAccountState.getAccountType(),
                    accountUpdateRequest.getNewCryptoPassword(),
                    timestamp
            );

            saveAccountAssetService.createTransaction(accountRecord);
        } else {
            accountRepository.save(account);
        }
    }

}
