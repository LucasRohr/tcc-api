package com.service.common.component.chaincode.user.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class GetUserAssetByIdFunction extends BaseChaincodeFunction {
    private static final String NAME = "queryByUserAssetId";

    public GetUserAssetByIdFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
