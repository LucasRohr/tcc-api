package com.service.common.component.chaincode.file;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;

public class FileAssetChaincode extends BaseChaincode {
    private static final String NAME = "filecc";

    private static final String VERSION = "1.6";

    public FileAssetChaincode(BaseChaincodeFunction function) {
        super(NAME, VERSION, function);
    }
}
