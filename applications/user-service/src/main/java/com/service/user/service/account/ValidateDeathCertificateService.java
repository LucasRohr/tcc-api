package com.service.user.service.account;

import com.service.common.domain.fabric.certificate.DeathCertificateRecordModel;
import com.service.common.service.fabric.account.InitiateDeathCertificateValidationService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateDeathCertificateService {

    @Autowired
    private InitiateDeathCertificateValidationService initiateDeathCertificateValidationService;

    public void validateDeathCertificate(String hash) throws ProposalException, InvalidArgumentException {
        // logic
        DeathCertificateRecordModel deathCertificateRecordModel = new DeathCertificateRecordModel(hash);
        initiateDeathCertificateValidationService.createTransaction(deathCertificateRecordModel);
    }
}
