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

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private PassOwnerAwayService passOwnerAwayService;

    @Autowired
    private ActivateHeirsHeritagesServices activateHeirsHeritagesServices;

    public void validateDeathCertificate(ValidateDeathCertificateRequest request)
            throws ProposalException, InvalidArgumentException {

        Owner owner = heirRepository.getHeirByAccountId(request.getHeirId()).getOwner();

        DeathCertificateRecordModel deathCertificateRecordModel =
                new DeathCertificateRecordModel(request.getCertificateHashCode(), owner.getId());

        String validationResponse =
                initiateDeathCertificateValidationService.createTransaction(deathCertificateRecordModel).get(0);

        if(validationResponse != null) {
            System.out.println("\n====== RESPONSE CC ========\n");
            System.out.println(validationResponse);
            System.out.println("\n======================\n");

//
//            passOwnerAwayService.passAway(owner);
//            activateHeirsHeritagesServices.activateHeirs(owner.getId());
        }

    }
}
