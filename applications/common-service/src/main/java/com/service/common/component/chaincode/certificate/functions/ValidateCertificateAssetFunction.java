package com.service.common.component.chaincode.certificate.functions;

import com.service.common.component.chaincode.BaseChaincodeFunction;

public class ValidateCertificateAssetFunction extends BaseChaincodeFunction {
    private static final String NAME = "validateDeathCertificate";

    public ValidateCertificateAssetFunction(final String[] arguments) {
        super(NAME, arguments);
    }
}
