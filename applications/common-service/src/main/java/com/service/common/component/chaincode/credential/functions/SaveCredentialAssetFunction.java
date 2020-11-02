package com.service.common.component.chaincode.credential.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class SaveCredentialAssetFunction extends BaseChaincodeFunction {
    private static final String NAME = "createCredentialAsset";

    public SaveCredentialAssetFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
