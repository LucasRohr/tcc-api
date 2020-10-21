package com.service.common.component.chaincode.user;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;

public class UserAssetChaincode extends BaseChaincode {
    private static final String NAME = "usercc";

    private static final String VERSION = "1.6";

    public UserAssetChaincode(BaseChaincodeFunction function) {
        super(NAME, VERSION, function);
    }
}
