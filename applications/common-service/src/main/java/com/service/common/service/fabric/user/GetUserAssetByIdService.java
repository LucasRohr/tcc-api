package com.service.common.service.fabric.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.user.UserAssetChaincode;
import com.service.common.component.chaincode.user.functions.GetUserAssetByIdFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.user.UserAsset;
import com.service.common.exceptions.InvalidProposalResponseException;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
public class GetUserAssetByIdService {

    @Autowired
    private ChannelClient channelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public UserAsset getUserAssetById(Long userId) throws ProposalException, InvalidArgumentException, IOException {
        final String[] arguments = mapArguments(userId);
        final BaseChaincodeFunction baseChaincodeFunction = new GetUserAssetByIdFunction(arguments);
        final BaseChaincode baseChaincode = new UserAssetChaincode(baseChaincodeFunction);

        final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
                .stream()
                .findFirst()
                .orElseThrow(InvalidProposalResponseException::new)
                .getMessage());

        final List<UserAsset> userAssets = Arrays.asList(objectMapper.readValue(response, UserAsset[].class));

        return userAssets.get(0);
    }

    private String[] mapArguments(Long userId) {
        return Stream.of(String.valueOf(userId)).toArray(String[]::new);
    }
}
