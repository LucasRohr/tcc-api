package com.service.credential.services;

import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.common.service.fabric.credential.GetCredentialAssetsByOwnerIdService;
import com.service.credential.controllers.response.CredentialResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOwnerCredentialsService {

    @Autowired
    private GetCredentialAssetsByOwnerIdService getCredentialAssetsByOwnerIdService;

    public List<CredentialResponse> getOwnerCredentials(Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {
        List<CredentialResponse> credentialsList = new ArrayList<>();

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
            List<CredentialAsset> inactiveCredentials = allCredentials.stream()
                    .filter(credential ->
                                credential.getCredentialId() == credentialAsset.getCredentialId() &&
                                        !credential.getIsActive()
                    ).collect(Collectors.toList());

            CredentialAsset inactiveCredential = inactiveCredentials.size() > 0 ? inactiveCredentials.get(0) : null;

            if(inactiveCredential == null) {
                String heirsIdsString = credentialAsset.getHeirsIds()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "");

                List<Long> heirsIds = heirsIdsString.length() > 0 ?
                        Arrays.stream(heirsIdsString.split(","))
                        .map(Long::parseLong).collect(Collectors.toList())
                        : new ArrayList<>();

                CredentialResponse responseCredential = new CredentialResponse(
                        credentialAsset.getCredentialId(),
                        credentialAsset.getName(),
                        credentialAsset.getDescription(),
                        credentialAsset.getLink(),
                        credentialAsset.getLogin(),
                        credentialAsset.getPassword(),
                        credentialAsset.getCredentialOwnerId(),
                        heirsIds,
                        credentialAsset.getIsActive(),
                        credentialAsset.getCreatedAt()
                );

                if(credentialsList.size() == 0) {
                    credentialsList.add(responseCredential);
                }

                List<CredentialResponse> assetsInList = credentialsList.stream()
                        .filter(
                                credential -> credential.getCredentialId().equals(responseCredential.getCredentialId())
                        ).collect(Collectors.toList());


                CredentialResponse assetInList = assetsInList.size() > 0 ? assetsInList.get(0) : null;

                if(assetInList == null) {
                    credentialsList.add(responseCredential);
                }
            }
        });

        return credentialsList;
    }

}
