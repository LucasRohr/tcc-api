package com.service.common.service.fabric.file;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.file.FileAssetChaincode;
import com.service.common.component.chaincode.file.functions.SaveFileAssetFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.file.FileRecordModel;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SaveFileAssetService {

    private ChannelClient channelClient;

    public SaveFileAssetService(ChannelClient channelClient) {
        this.channelClient = channelClient;
    }

    public List<String> createTransaction(FileRecordModel recordModel)
            throws ProposalException, InvalidArgumentException {
        final String[] arguments = recordModel.toArguments();
        final BaseChaincodeFunction baseChaincodeFunction = new SaveFileAssetFunction(arguments);
        final BaseChaincode baseChaincode = new FileAssetChaincode(baseChaincodeFunction);

        final Collection<ProposalResponse> proposalResponses = channelClient.sendTransactionProposal(baseChaincode);

        return proposalResponses
                .stream()
                .map(ChaincodeResponse::getTransactionID)
                .collect(Collectors.toList());
    }
}
