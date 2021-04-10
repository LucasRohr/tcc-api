package com.service.common.service.fabric.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.file.FileAssetChaincode;
import com.service.common.component.chaincode.file.functions.GetFileAssetByIdFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.file.FileAsset;
import com.service.common.exceptions.InvalidProposalResponseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GetFileAssetByIdService {

    @Autowired
    private ChannelClient channelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public List<FileAsset> getFileAssetById(Long fileId) throws ProposalException, InvalidArgumentException, IOException {
        final String[] arguments = mapArguments(fileId);
        final BaseChaincodeFunction baseChaincodeFunction = new GetFileAssetByIdFunction(arguments);
        final BaseChaincode baseChaincode = new FileAssetChaincode(baseChaincodeFunction);

        final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode))
                .stream()
                .findFirst()
                .orElseThrow(InvalidProposalResponseException::new)
                .getMessage();

        return Arrays.asList(objectMapper.readValue(response, FileAsset[].class));
    }

    private String[] mapArguments(Long fileId) {
        return Stream.of(String.valueOf(fileId)).toArray(String[]::new);
    }
}
