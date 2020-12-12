package com.service.credential.services;

import com.service.common.domain.Heir;
import com.service.common.repository.HeirRepository;
import com.service.credential.controllers.response.CredentialHeirResponse;
import com.service.credential.controllers.response.CredentialResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetHeirsForCredentialService {

    @Autowired
    private GetOwnerCredentialsService getOwnerCredentialsService;

    @Autowired
    private HeirRepository heirRepository;

    public List<CredentialHeirResponse> getHeirs(Long ownerId, Long credentialId)
            throws ProposalException, IOException, InvalidArgumentException {

        List<CredentialResponse> credentialResponses = getOwnerCredentialsService.getOwnerCredentials(ownerId);
        List<Heir> ownerHeirs = heirRepository.getOwnerHeirs(ownerId);
        List<CredentialHeirResponse> credentialHeirResponses = new ArrayList<>();

        CredentialResponse selectedCredential = credentialResponses.stream()
                .filter(credentialResponse -> credentialResponse.getCredentialId() == credentialId)
                .collect(Collectors.toList())
                .get(0);

        ownerHeirs.forEach(heir -> {
            boolean heirHasCredential = selectedCredential.getHeirsIds().contains(heir.getId());

            CredentialHeirResponse credentialHeirResponse = new CredentialHeirResponse(
                    heir.getId(),
                    heir.getAccount().getUser().getName(),
                    heir.getAccount().getName(),
                    heir.getAccount().getUser().getEmail(),
                    heirHasCredential
            );

            credentialHeirResponses.add(credentialHeirResponse);
        });

        credentialHeirResponses.sort(
                new Comparator<CredentialHeirResponse>() {
                    @Override
                    public int compare(CredentialHeirResponse heirA, CredentialHeirResponse heirB) {
                        return heirA.isHasItem() ? -1 : 1;
                    }
                }
        );

        return credentialHeirResponses;
    }

}
