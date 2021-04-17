package com.service.common.service.fabric.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.account.AccountAssetChaincode;
import com.service.common.component.chaincode.account.functions.GetAccountAssetByIdFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.user.UserAsset;
import com.service.common.exceptions.InvalidProposalResponseException;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
public class GetAccountAssetByIdService {

    @Autowired
    private ChannelClient channelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public AccountAsset getUserAssetById(Long accountId) throws ProposalException, InvalidArgumentException, IOException {
        final String[] arguments = mapArguments(accountId);
        final BaseChaincodeFunction baseChaincodeFunction = new GetAccountAssetByIdFunction(arguments);
        final BaseChaincode baseChaincode = new AccountAssetChaincode(baseChaincodeFunction);

        final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
                .stream()
                .findFirst()
                .orElseThrow(InvalidProposalResponseException::new)
                .getMessage());

        final List<AccountAsset> accountAssets = Arrays.asList(objectMapper.readValue(response, AccountAsset[].class));
        accountAssets.sort(
                (accountA, accountB) -> accountA.getTimestamp() > accountB.getTimestamp() ? -1 : 1
        );

        if (accountAssets.size() > 0) {
            return accountAssets.get(0);
        }
        throw new Error("No accountAsset found");
    }

    private String[] mapArguments(Long accountId) {
        return Stream.of(String.valueOf(accountId)).toArray(String[]::new);
    }
}
