package chaincode.credential;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CredentialChaincode extends ChaincodeBase {

	private final static Logger LOGGER = Logger.getLogger(CredentialChaincode.class.getName());

	private Gson gson;

	private CredentialChaincode() {
		this.gson = new Gson();
	}

	@Override
	public Response init(ChaincodeStub stub) {
		return ResponseUtils.newSuccessResponse();
	}

	@Override
	public Response invoke(final ChaincodeStub stub) {
		final List<String> params = stub.getParameters();

		switch (stub.getFunction()) {
			case "createCredentialAsset":
				return createCredentialAsset(stub, params);
			case "queryByCredentialsOwnerId":
				return queryByCredentialsOwnerId(stub, params);
			default:
				return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
		}
	}

	@Transaction
	private Response createCredentialAsset(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final String credentialState = gson.toJson(mapParamsToCredential(params));

		stub.putStringState(key, credentialState);

		return ResponseUtils.newSuccessResponse(credentialState);
	}

	private Response queryByCredentialsOwnerId(final ChaincodeStub stub, final List<String> params) {
		final long ownerId = Long.parseLong(params.get(0));
		final String query = "{ \"selector\": { \"credentialOwnerId\": " + ownerId + " } }";

		return ResponseUtils.newSuccessResponse(getQueryResult(stub, query));
	}

	private Credential mapParamsToCredential(List<String> params) {
		final Long credentialId = Long.parseLong(params.get(0));
		final String name = params.get(1);
		final String description = params.get(2);
		final String link = params.get(3);
		final String login = params.get(4);
		final String password = params.get(5);
		final Long credentialOwnerId = Long.parseLong(params.get(6));
		final String heirsIds =	params.get(7);
		final Boolean isActive = Boolean.parseBoolean(params.get(8));
		final Long createdAt = Long.parseLong(params.get(9));
		final String symmetricKey = params.get(10);

		return Credential
				.builder()
				.credentialId(credentialId)
				.name(name)
				.description(description)
				.link(link)
				.login(login)
				.password(password)
				.credentialOwnerId(credentialOwnerId)
				.heirsIds(heirsIds)
				.isActive(isActive)
				.createdAt(createdAt)
				.symmetricKey(symmetricKey)
				.build();
	}

	private String getQueryResult(ChaincodeStub stub, String query) {
		final QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(query);

		String result = StreamSupport.stream(queryResult.spliterator(), false)
				.map(KeyValue::getStringValue)
				.collect(Collectors.toList())
				.toString();

		return result;
	}

	public static void main(String[] args) {
		new CredentialChaincode().start(args);
	}
}
