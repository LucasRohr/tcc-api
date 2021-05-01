package com.service.user.service.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.domain.Owner;
import com.service.common.domain.fabric.certificate.DeathCertificateRecordModel;
import com.service.common.repository.HeirRepository;
import com.service.common.service.fabric.certificate.InitiateDeathCertificateValidationService;
import com.service.user.dto.CertificateValidationResponseDto;
import com.service.user.dto.ValidateDeathCertificateRequest;
import com.service.user.exceptions.InvalidDeathCertificateException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private ObjectMapper objectMapper;

    public void validateDeathCertificate(ValidateDeathCertificateRequest request)
            throws ProposalException, InvalidArgumentException, JsonProcessingException {

        Owner owner = heirRepository.getHeirByAccountId(request.getHeirId()).getOwner();

        DeathCertificateRecordModel deathCertificateRecordModel =
                new DeathCertificateRecordModel(owner.getId(), !owner.getIsAlive(), request.getCertificateHashCode());

        CertificateValidationResponseDto validationResponse = objectMapper.readValue(
                initiateDeathCertificateValidationService.createTransaction(deathCertificateRecordModel),
                CertificateValidationResponseDto.class
        );

        boolean isHashCodeValid =
                validationResponse.getHashCodeValidation() != null && validationResponse.getHashCodeValidation() != 0;

        if(!isHashCodeValid) {
            passOwnerAwayService.passAway(owner);
            activateHeirsHeritagesServices.activateHeirs(owner.getId());
        } else {
            throw new InvalidDeathCertificateException();
        }

    }
}
