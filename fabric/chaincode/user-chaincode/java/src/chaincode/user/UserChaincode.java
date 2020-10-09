package chaincode.user;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserChaincode extends ChaincodeBase {

	private final static Logger LOGGER = Logger.getLogger(UserChaincode.class.getName());

	private Gson gson;

	private UserChaincode() {
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
			case "createUserAsset":
				createUser(stub, params);
			case "queryByUserAssetId":
				queryByUserId(stub, params);
			default:
				return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
		}
	}

	@Transaction
	private Response createUser(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final String userState = gson.toJson(mapParamsToUser(params));

		stub.putStringState(key, userState);

		return ResponseUtils.newSuccessResponse(userState);
	}

	private Response queryByUserId(final ChaincodeStub stub, final List<String> params) {
		final long userId = Long.parseLong(params.get(0));
		final String query = "{ \"selector\": { \"userId\": " + userId + " } }";

		return ResponseUtils.newSuccessResponse(getQueryResult(stub, query));
	}

	private User mapParamsToUser(List<String> params) {
		final Long userId = Long.parseLong(params.get(0));
		final String cpf = params.get(1);
		final LocalDateTime birthday = LocalDateTime.parse(params.get(2));
		final String privateKey = params.get(3);
		final String storageHash = params.get(4);

		return User
				.builder()
				.userId(userId)
				.cpf(cpf)
				.birthday(birthday)
				.privateKey(privateKey)
				.storageHash(storageHash)
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
		new UserChaincode().start(args);
	}
}
