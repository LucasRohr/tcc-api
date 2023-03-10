package com.service.common.component.fabric;

import com.service.common.component.chaincode.BaseChaincode;
import lombok.Getter;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
@Component
public class ChannelClient {

	private Channel channel;

	private FabricClient fabricClient;

	ChannelClient(Channel channel, FabricClient fabricClient) {
		this.channel = channel;
		this.fabricClient = fabricClient;
	}

	public Collection<ProposalResponse> sendTransactionProposal(BaseChaincode baseChaincode) throws ProposalException, InvalidArgumentException {
		final TransactionProposalRequest request = fabricClient.getInstance().newTransactionProposalRequest();
		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setChaincodeVersion(baseChaincode.getVersion());
		request.setProposalWaitTime(30000);

		final Collection<ProposalResponse> response = channel.sendTransactionProposal(request, channel.getPeers());
		channel.sendTransaction(response);

		return response;
	}

	public Collection<ProposalResponse> queryByChaincode(BaseChaincode baseChaincode) throws InvalidArgumentException, ProposalException {
		final QueryByChaincodeRequest request = fabricClient.getInstance().newQueryProposalRequest();

		request.setChaincodeID(ChaincodeID
				.newBuilder()
				.setName(baseChaincode.getName())
				.build());
		request.setFcn(baseChaincode.getFunction().getName());
		request.setArgs(baseChaincode.getFunction().getArguments());
		request.setChaincodeLanguage(TransactionRequest.Type.JAVA);
		request.setChaincodeVersion(baseChaincode.getVersion());
		request.setProposalWaitTime(30000);

		return channel.queryByChaincode(request);
	}
}
