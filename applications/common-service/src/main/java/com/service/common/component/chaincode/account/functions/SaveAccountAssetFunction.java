package com.service.common.component.chaincode.account.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class SaveAccountAssetFunction extends BaseChaincodeFunction {
    private static final String NAME = "createAccountAsset";

    public SaveAccountAssetFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
