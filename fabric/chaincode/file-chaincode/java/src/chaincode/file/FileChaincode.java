package chaincode.file;

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

public class FileChaincode extends ChaincodeBase {

    private final static Logger LOGGER = Logger.getLogger(FileChaincode.class.getName());

    private Gson gson;

    private FileChaincode() {
        this.gson = new Gson();
    }

    @Override
    public Response init(ChaincodeStub chaincodeStub) {
        return ResponseUtils.newSuccessResponse();
    }

    @Override
    public Response invoke(final ChaincodeStub chaincodeStub) {
        final List<String> params = chaincodeStub.getParameters();

        switch (chaincodeStub.getFunction()) {
            case "createFileAsset":
                return createFile(chaincodeStub, params);
            case "queryByFileAssetId":
                return queryByFileId(chaincodeStub, params);
            default:
                return ResponseUtils.newErrorResponse(
                        String.format("No such function %s exist", chaincodeStub.getFunction()));
        }
    }

    @Transaction
    private Response createFile(final ChaincodeStub chaincodeStub, final List<String> params) {
        final String key = params.get(0);
        final String fileState = gson.toJson(mapParamsToFile(params));

        chaincodeStub.putStringState(key, fileState);

        return ResponseUtils.newSuccessResponse(fileState);
    }

    private Response queryByFileId(final ChaincodeStub stub, final List<String> params) {
        final long fileId = Long.parseLong(params.get(0));
        final String query = "{ \"selector\": { \"fileId\": " + fileId + " } }";

        return ResponseUtils.newSuccessResponse(getQueryResult(stub, query));
    }

    private File mapParamsToFile(List<String> params) {
        final Long fileId = Long.parseLong(params.get(0));
        final String symmetricKey = params.get(1);

        return File
                .builder()
                .fileId(fileId)
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
        new FileChaincode().start(args);
    }

}

