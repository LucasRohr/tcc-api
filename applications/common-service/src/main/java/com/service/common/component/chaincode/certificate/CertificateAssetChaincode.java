package com.service.common.component.chaincode.certificate;

import com.service.common.component.chaincode.BaseChaincode;
import com.service.common.component.chaincode.BaseChaincodeFunction;

public class CertificateAssetChaincode extends BaseChaincode {
    private static final String NAME = "certificatevalidationcc";

    private static final String VERSION = "1.6";

    public CertificateAssetChaincode(BaseChaincodeFunction function) {
        super(NAME, VERSION, function);
    }
}
