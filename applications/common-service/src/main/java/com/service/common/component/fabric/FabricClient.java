package com.service.common.component.fabric;

import lombok.Getter;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;


import java.lang.reflect.InvocationTargetException;

@Getter
public class FabricClient {

	private HFClient instance;

	public FabricClient(final User user) throws CryptoException, InvalidArgumentException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		instance = HFClient.createNewInstance();
		instance.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
		instance.setUserContext(user);
	}

	public ChannelClient createChannelClient(final String channelName) throws InvalidArgumentException {
		final Channel channel = instance.newChannel(channelName);
		return new ChannelClient(channel, this);
	}
}
