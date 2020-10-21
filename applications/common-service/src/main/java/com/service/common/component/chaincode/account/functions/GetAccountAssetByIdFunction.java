package com.service.common.component.chaincode.account.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class GetAccountAssetByIdFunction extends BaseChaincodeFunction {
    private static final String NAME = "queryByAccountAssetId";

    public GetAccountAssetByIdFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
