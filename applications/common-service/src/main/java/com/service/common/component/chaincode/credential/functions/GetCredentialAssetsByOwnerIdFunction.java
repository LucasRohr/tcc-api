package com.service.common.component.chaincode.credential.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class GetCredentialAssetsByOwnerIdFunction extends BaseChaincodeFunction {
    private static final String NAME = "queryByCredentialsOwnerId";

    public GetCredentialAssetsByOwnerIdFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
