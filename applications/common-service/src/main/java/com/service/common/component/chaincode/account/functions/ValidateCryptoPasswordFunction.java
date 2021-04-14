package com.service.common.component.chaincode.account.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class ValidateCryptoPasswordFunction extends BaseChaincodeFunction {
    private static final String NAME = "validateCryptoPassword";

    public ValidateCryptoPasswordFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
