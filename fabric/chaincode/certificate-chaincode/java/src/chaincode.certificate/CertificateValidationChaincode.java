package chaincode.certificate;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.contract.annotation.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

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
		final List<String> params = stub.getParameters();

		switch (stub.getFunction()) {
			case "validateDeathCertificate":
				try {
					return validateDeathCertificate(stub, params);
				} catch (IOException e) {
					e.printStackTrace();
				}
			default:
				return ResponseUtils.newErrorResponse(String.format("No such function %s exist", stub.getFunction()));
		}
	}

	@Transaction
	private Response validateDeathCertificate(final ChaincodeStub stub, final List<String> params) throws IOException {

		final String hash = params.get(0);
		// final String hash = "BVCBVCBCVBCVBVBVBVBVBVBVBVBVBVBv";
		final String url = "https://registrocivil.org.br:8443/api/carrinho/pedidos/validarCodigoHash/" + hash;
		String apiResponse;

		HttpURLConnection httpClient =
				(HttpURLConnection) new URL(url).openConnection();

		// optional default is GET
		httpClient.setRequestMethod("GET");
		httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
		httpClient.setRequestProperty(
				"Authorization",
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI2b2oxTDB2Ynl0bjMzMzRTWWJaNVFJdlpuVGhhZGVYeCIsImhhc2hfY29kZSI6IkhZVFlUWVlUVFJZVFlUWVRSWVRZVFlUUllSVFlSVFlyIn0.TnxxVmcKI2_Xbp8tL6fIpGCWrmTX67vLlQdXRm_Hs4I"
		);
		httpClient.setRequestProperty(
				"apikey",
				"7CojClx9l62Mz6SJcEHFWZfK2NtSHXgI"
		);

		int responseCode = httpClient.getResponseCode();
		if (responseCode != 200) {
			throw new ChaincodeException("Pokemon not found :/");
		}

		// Sending 'GET' request to URL
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(httpClient.getInputStream()))) {

			StringBuilder response = new StringBuilder();
			String line;

			while ((line = in.readLine()) != null) {
				response.append(line);
			}

			//print result
			apiResponse = response.toString();

		}
		return ResponseUtils.newSuccessResponse(apiResponse);
	}

	public static void main(String[] args) {
		new CertificateValidationChaincode().start(args);
	}
}
