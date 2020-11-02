package com.service.common.service.fabric.credential;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.credential.CredentialAssetChaincode;
import com.service.common.component.chaincode.credential.functions.GetCredentialAssetsByOwnerIdFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.credential.CredentialAsset;
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
public class GetCredentialAssetsByOwnerIdService {

    @Autowired
    private ChannelClient channelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public List<CredentialAsset> getCredentialsByOwnerId(Long ownerId) throws ProposalException, InvalidArgumentException, IOException {
        final String[] arguments = mapArguments(ownerId);
        final BaseChaincodeFunction baseChaincodeFunction = new GetCredentialAssetsByOwnerIdFunction(arguments);
        final BaseChaincode baseChaincode = new CredentialAssetChaincode(baseChaincodeFunction);

        final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
                .stream()
                .findFirst()
                .orElseThrow(InvalidProposalResponseException::new)
                .getMessage());

        return Arrays.asList(objectMapper.readValue(response, CredentialAsset[].class));
    }

    private String[] mapArguments(Long ownerId) {
        return Stream.of(String.valueOf(ownerId)).toArray(String[]::new);
    }
}
