package com.service.common.component.chaincode.credential;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;

public class CredentialAssetChaincode extends BaseChaincode {
    private static final String NAME = "credentialcc";

    private static final String VERSION = "1.6";

    public CredentialAssetChaincode(BaseChaincodeFunction function) {
        super(NAME, VERSION, function);
    }
}
