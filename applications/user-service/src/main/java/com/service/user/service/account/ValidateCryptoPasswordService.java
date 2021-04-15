package com.service.user.service.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.common.domain.fabric.account.CryptoPasswordValidationModel;
import com.service.common.service.fabric.account.InitiateCryptoPasswordValidationService;
import com.service.user.controller.request.CryptoPasswordRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateCryptoPasswordService {

    @Autowired
    private InitiateCryptoPasswordValidationService initiateCryptoPasswordValidationService;

    public String validateCryptoPassword(CryptoPasswordRequest request) throws InvalidArgumentException, ProposalException, JsonProcessingException {

        CryptoPasswordValidationModel cryptoPasswordValidationModel
                = new CryptoPasswordValidationModel(request.getAccountId(), request.getCryptoPassword());

        return initiateCryptoPasswordValidationService.createTransaction(cryptoPasswordValidationModel);
    }

}
