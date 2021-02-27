package chaincode.certificate;

import com.google.gson.Gson;
import org.hyperledger.fabric.Logger;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ResponseUtils;
import org.hyperledger.fabric.contract.annotation.Transaction;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

public class CertificateValidationChaincode extends ChaincodeBase {

	private final static Logger LOGGER = Logger.getLogger(CertificateValidationChaincode.class.getName());

	private Gson gson;

	private CertificateValidationChaincode() {
		this.gson = new Gson();
	}

	@Override
	public Response init(ChaincodeStub stub) {
		AcceptAllCertificates.acceptAllCertificates();
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
	private Response validateDeathCertificate(final ChaincodeStub stub, final List<String> params) {
		final String key = params.get(0);
		final String certificateState = gson.toJson(mapParamsToCertificate(params));

		stub.putStringState(key, certificateState);

		String apiResponse = "";

		final String hash = params.get(2);

		final String url = "https://registrocivil.org.br:8443/api/carrinho/pedidos/validarCodigoHash/" + hash;
		try {
			HttpsURLConnection httpClient =
					(HttpsURLConnection) new URL(url).openConnection();

			httpClient.setRequestMethod("GET");
			httpClient.setRequestProperty("user-agent", "Mozilla/5.0");
			httpClient.setRequestProperty(
					"authorization",
					"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI2b2oxTDB2Ynl0bjMzMzRTWWJaNVF" +
							"JdlpuVGhhZGVYeCIsImhhc2hfY29kZSI6ImZzZGYxczFkZjFkZjY1ZjZzZGY2c2ZkZjZkczZmMX" +
							"NkIn0.WCUgzSlTFbeWodyP8z6oLYeGZD8o-jBqDRjxIALgguA"
			);

			httpClient.setRequestProperty(
					"apikey",
					"7CojClx9l62Mz6SJcEHFWZfK2NtSHXgI"
			);

			BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));

			StringBuilder response = new StringBuilder();
			String line;

			while ((line = in.readLine()) != null) {
				response.append(line);
			}

			in.close();

			apiResponse = response.toString();

			int responseCode = httpClient.getResponseCode();

			if (responseCode != 200) {
				throw new ChaincodeException("Houve um problema com a requisição. Tente novamente mais tarde.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseUtils.newSuccessResponse(apiResponse);
	}

	private CertificateValidation mapParamsToCertificate(List<String> params) {
		final Long ownerId = Long.parseLong(params.get(0));
		final Boolean isHeritageActive =  Boolean.parseBoolean(params.get(1));
		final String hashCode = params.get(2);

		return CertificateValidation
				.builder()
				.ownerId(ownerId)
				.isHeritageActive(isHeritageActive)
				.hashCode(hashCode)
				.build();
	}

	public static void main(String[] args) {
		new CertificateValidationChaincode().start(args);
	}
}
