package com.service.common.service.account;

import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GetAccountAssetByIdCommonService {

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    public AccountAsset getAccount(Long id) {
        try {
            return getAccountAssetByIdService.getUserAssetById(id);
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
