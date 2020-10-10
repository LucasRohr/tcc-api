package com.service.common.service.fabric.user;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.user.UserAssetChaincode;
import com.service.common.component.chaincode.user.functions.SaveUserAssetFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.user.UserRecordModel;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SaveUserAssetService {

    private ChannelClient channelClient;

    public SaveUserAssetService(ChannelClient channelClient) {
        this.channelClient = channelClient;
    }

    public List<String> createTransaction(UserRecordModel recordModel) throws Exception {
        final String[] arguments = recordModel.toArguments();
        final BaseChaincodeFunction baseChaincodeFunction = new SaveUserAssetFunction(arguments);
        final BaseChaincode baseChaincode = new UserAssetChaincode(baseChaincodeFunction);

        final Collection<ProposalResponse> proposalResponses = channelClient.sendTransactionProposal(baseChaincode);

        return proposalResponses
                .stream()
                .map(ChaincodeResponse::getTransactionID)
                .collect(Collectors.toList());
    }
}