package com.service.common.service.fabric.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.account.AccountAssetChaincode;
import com.service.common.component.chaincode.account.functions.SetCryptoPasswordFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.account.CryptoPasswordValidationModel;
import com.service.common.exceptions.InvalidProposalResponseException;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class SetCryptoPasswordService {

    @Autowired
    private ChannelClient channelClient;

    public String createTransaction(CryptoPasswordValidationModel validationModel) throws InvalidArgumentException, ProposalException, JsonProcessingException {
        final String[] arguments = validationModel.toArguments();
        final BaseChaincodeFunction baseChaincodeFunction = new SetCryptoPasswordFunction(arguments);
        final BaseChaincode baseChaincode = new AccountAssetChaincode(baseChaincodeFunction);

        channelClient.sendTransactionProposal(baseChaincode);

        final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
                .stream()
                .findFirst()
                .orElseThrow(InvalidProposalResponseException::new)
                .getMessage());

//        return Boolean.parseBoolean(response);
        return response;
    }
}
