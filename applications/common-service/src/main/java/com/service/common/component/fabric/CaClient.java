package com.service.common.component.fabric;

import com.service.common.domain.fabric.FabricUser;
import com.service.common.helpers.FabricUserDeserializer;
import com.service.common.helpers.FabricUserSerializer;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

@Component
public class CaClient {

	private String org1;

	private String org1Msp;

	private String caOrg1Url;

	private String adminUsername;

	private String adminPassword;

	private HFCAClient hfcaClient;

	private FabricUserDeserializer fabricUserDeserializer;

	private FabricUserSerializer fabricUserSerializer;

	private CaClient(@Value("${fabric.org1.name}") String org1,
	                 @Value("${fabric.org1.msp}") String org1Msp,
	                 @Value("${fabric.ca.org1.url}") String caOrg1Url,
	                 @Value("${fabric.admin.username}") String adminUsername,
	                 @Value("${fabric.admin.password}") String adminPassword,
	                 FabricUserDeserializer fabricUserDeserializer,
	                 FabricUserSerializer fabricUserSerializer) throws MalformedURLException, IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
		this.org1 = org1;
		this.org1Msp = org1Msp;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.fabricUserDeserializer = fabricUserDeserializer;
		this.fabricUserSerializer = fabricUserSerializer;
		this.hfcaClient = HFCAClient.createNewInstance(caOrg1Url, null);
		hfcaClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
	}

	public FabricUser registerAdminUser() throws Exception {
		final FabricUser fabricUserModel = fabricUserDeserializer.readFabricUser(org1, adminUsername);

		if (fabricUserModel != null) {
			return fabricUserModel;
		}

		final FabricUser adminFabricUser = new FabricUser();
		adminFabricUser.setName(adminUsername);
		adminFabricUser.setPassword(adminPassword);
		adminFabricUser.setAffiliation(org1);
		adminFabricUser.setMspId(org1Msp);

		final Enrollment adminEnrollment = hfcaClient.enroll(adminFabricUser.getName(), adminFabricUser.getPassword());
		adminFabricUser.setEnrollment(adminEnrollment);

		return fabricUserSerializer.writeFabricUser(adminFabricUser);
	}
}
