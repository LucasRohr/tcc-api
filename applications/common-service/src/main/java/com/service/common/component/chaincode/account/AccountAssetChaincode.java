package com.service.common.component.chaincode.account;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;

public class AccountAssetChaincode extends BaseChaincode {
    private static final String NAME = "accountcc";

    private static final String VERSION = "1.6";

    public AccountAssetChaincode(BaseChaincodeFunction function) {
        super(NAME, VERSION, function);
    }
}
