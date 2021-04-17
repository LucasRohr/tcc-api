package com.service.user.service.account;

import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.user.controller.request.CryptoPasswordRequest;
import com.service.user.dto.ValidateCryptoPasswordResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ValidateCryptoPasswordService {

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    public ValidateCryptoPasswordResponse validateCryptoPassword(CryptoPasswordRequest request)
            throws InvalidArgumentException, ProposalException, IOException {
        AccountAsset accountAsset = getAccountAssetByIdService.getUserAssetById(request.getAccountId());

        boolean isPasswordValid =
                bCryptEncoder.matches(request.getCryptoPassword(), accountAsset.getCryptoPassword());

        return new ValidateCryptoPasswordResponse(isPasswordValid);
    }

}
