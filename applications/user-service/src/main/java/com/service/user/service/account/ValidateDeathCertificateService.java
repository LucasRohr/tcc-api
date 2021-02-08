package com.service.user.service.account;

import com.service.common.domain.Owner;
import com.service.common.domain.fabric.certificate.DeathCertificateRecordModel;
import com.service.common.repository.HeirRepository;
import com.service.common.service.fabric.account.InitiateDeathCertificateValidationService;
import com.service.user.dto.ValidateDeathCertificateRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateDeathCertificateService {

    @Autowired
    private InitiateDeathCertificateValidationService initiateDeathCertificateValidationService;

    @Autowired
    private UpdateAccountService updateAccountService;

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private PassOwnerAwayService passOwnerAwayService;

    public void validateDeathCertificate(ValidateDeathCertificateRequest request) throws ProposalException, InvalidArgumentException {
        // logic
        DeathCertificateRecordModel deathCertificateRecordModel = new DeathCertificateRecordModel(request.getCertificateHashCode());

        // return initiateDeathCertificateValidationService.createTransaction(deathCertificateRecordModel);

        if (true) {
            Owner owner = heirRepository.getHeirByAccountId(request.getHeirId()).getOwner();
            passOwnerAwayService.passAway(owner);
        }
    }
}
