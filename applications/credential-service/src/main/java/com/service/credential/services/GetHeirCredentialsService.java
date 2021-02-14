package com.service.credential.services;

import com.service.common.domain.Heir;
import com.service.common.dto.CredentialResponseWithouPassword;
import com.service.common.repository.HeirRepository;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetHeirCredentialsService {

    @Autowired
    private GetCredentialsWithoutPasswordService getCredentialsWithoutPasswordService;

    @Autowired
    private HeirRepository heirRepository;

    public List<CredentialResponseWithouPassword> getHeirCredentials(Long heirId)
            throws ProposalException, IOException, InvalidArgumentException {
        Heir heir = heirRepository.findById(heirId).get();
        List<CredentialResponseWithouPassword> ownerCredentials = getCredentialsWithoutPasswordService.getCredentials(
                heir.getOwner().getId()
        );

         return ownerCredentials.stream().filter(
                 credentialResponse -> credentialResponse.getHeirsIds().contains(heirId)
         ).collect(Collectors.toList());
    }

}
