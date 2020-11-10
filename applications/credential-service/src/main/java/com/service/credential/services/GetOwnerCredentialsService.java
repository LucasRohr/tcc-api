package com.service.credential.services;

import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.common.service.fabric.credential.GetCredentialAssetsByOwnerIdService;
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
public class GetOwnerCredentialsService {

    @Autowired
    private GetCredentialAssetsByOwnerIdService getCredentialAssetsByOwnerIdService;

    public List<CredentialAsset> getOwnerCredentials(Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {
        List<CredentialAsset> credentialsList = new ArrayList<>();

        List<CredentialAsset> allCredentials = getCredentialAssetsByOwnerIdService.getCredentialsByOwnerId(ownerId);

        allCredentials.sort(
            new Comparator<CredentialAsset>() {
                @Override
                public int compare(CredentialAsset credentialA, CredentialAsset credentialB) {
                    Long idA = credentialA.getCredentialId();
                    Long idB = credentialB.getCredentialId();

                    Long createdAtA = credentialA.getCreatedAt();
                    Long createdAtB = credentialB.getCreatedAt();

                    return idA > idB || createdAtA > createdAtB ? -1 : 1;
                }
            }
        );

        allCredentials.forEach(credentialAsset -> {
            List<CredentialAsset> assetsInList = credentialsList.stream()
                    .filter(
                            credential -> credential.getCredentialId().equals(credentialAsset.getCredentialId())
                    ).collect(Collectors.toList());

            CredentialAsset assetInList = assetsInList.get(0);

            if(assetInList != null) {
                credentialsList.add(credentialAsset);
            }
        });

        return credentialsList;
    }

}
