package com.service.common.component.chaincode.user.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class SaveUserAssetFunction extends BaseChaincodeFunction {
    private static final String NAME = "createUserAsset";

    public SaveUserAssetFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
