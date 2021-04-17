package com.service.common.component.chaincode.file.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class SaveFileAssetFunction extends BaseChaincodeFunction {
    private static final String NAME = "createFileAsset";

    public SaveFileAssetFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
