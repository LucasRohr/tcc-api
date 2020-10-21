package chaincode.certificate;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;

public class CertificateValidationChaincode extends ChaincodeBase {

	private final static Logger LOGGER = Logger.getLogger(CertificateValidationChaincode.class.getName());

	private Gson gson;

	private CertificateValidationChaincode() {
		this.gson = new Gson();
	}

	@Override
	public Response init(ChaincodeStub stub) {
		return ResponseUtils.newSuccessResponse();
	}

	@Override
	public Response invoke(final ChaincodeStub stub) {
		return ResponseUtils.newSuccessResponse("ok");
	}

	public static void main(String[] args) {
		new CertificateValidationChaincode().start(args);
	}
}
