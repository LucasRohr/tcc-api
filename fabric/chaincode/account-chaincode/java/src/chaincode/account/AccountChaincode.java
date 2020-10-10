package chaincode.account;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AccountChaincode extends ChaincodeBase {

    private final static Logger LOGGER = Logger.getLogger(AccountChaincode.class.getName());

    private Gson gson;

    private AccountChaincode() {
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
            case "createAccountAsset":
                return createAccountAsset(stub, params);
            case "queryByAccountAssetId":
                return queryByAccountAssetId(stub, params);
            default:
                return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
        }
    }

    @Transaction
    private Response createAccountAsset(final ChaincodeStub stub, final List<String> params) {
        final String key = params.get(0);
        final String accountState = gson.toJson(mapParamsToUser(params));

        stub.putStringState(key, accountState);

        return ResponseUtils.newSuccessResponse(accountState);
    }

    private Response queryByAccountAssetId(final ChaincodeStub stub, final List<String> params) {
        final long accountId = Long.parseLong(params.get(0));
        final String query = "{ \"selector\": { \"accountId\": " + accountId + " } }";

        return ResponseUtils.newSuccessResponse(getQueryResult(stub, query));
    }

    private Account mapParamsToUser(List<String> params) {
        final Long accountId = Long.parseLong(params.get(0));
        final String privateKey = params.get(1);
        final String publicKey = params.get(2);
        final String accountType = params.get(3);

        return Account
                .builder()
                .accountId(accountId)
                .privateKey(privateKey)
                .publicKey(publicKey)
                .accountType(accountType)
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
        new AccountChaincode().start(args);
    }
}
