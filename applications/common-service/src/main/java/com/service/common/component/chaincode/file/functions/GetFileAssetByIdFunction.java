package com.service.common.component.chaincode.file.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class GetFileAssetByIdFunction extends BaseChaincodeFunction {
    private static final String NAME = "queryByFileAssetId";

    public GetFileAssetByIdFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
