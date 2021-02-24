package com.service.common.service.fabric.certificate;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;
import com.service.common.component.chaincode.certificate.CertificateAssetChaincode;
import com.service.common.component.chaincode.certificate.functions.ValidateCertificateAssetFunction;
import com.service.common.component.fabric.ChannelClient;
import com.service.common.domain.fabric.certificate.DeathCertificateRecordModel;
import com.service.common.exceptions.InvalidProposalResponseException;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InitiateDeathCertificateValidationService {

    private ChannelClient channelClient;

    public InitiateDeathCertificateValidationService(ChannelClient channelClient) {
        this.channelClient = channelClient;
    }

    public String createTransaction(DeathCertificateRecordModel recordModel)
            throws InvalidArgumentException, ProposalException {

        final String[] arguments = recordModel.toArguments();
        final BaseChaincodeFunction baseChaincodeFunction = new ValidateCertificateAssetFunction(arguments);
        final BaseChaincode baseChaincode = new CertificateAssetChaincode(baseChaincodeFunction);

        channelClient.sendTransactionProposal(baseChaincode);

        final String response = Objects.requireNonNull(channelClient.queryByChaincode(baseChaincode)
                .stream()
                .findFirst()
                .orElseThrow(InvalidProposalResponseException::new)
                .getMessage());

        return response;
    }
}
