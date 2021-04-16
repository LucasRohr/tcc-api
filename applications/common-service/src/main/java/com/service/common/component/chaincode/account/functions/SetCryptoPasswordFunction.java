package com.service.common.component.chaincode.account.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class SetCryptoPasswordFunction extends BaseChaincodeFunction {
    private static final String NAME = "setCryptoPassword";

    public SetCryptoPasswordFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
