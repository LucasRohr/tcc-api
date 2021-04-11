package com.service.credential.services;

import com.service.common.crypto.SymmetricCrypto;
import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.common.service.fabric.credential.GetCredentialAssetsByOwnerIdService;
import com.service.credential.controllers.response.CredentialResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOwnerCredentialsService {

    @Autowired
    private GetCredentialAssetsByOwnerIdService getCredentialAssetsByOwnerIdService;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    public List<CredentialResponse> getOwnerCredentials(Long ownerId)
            throws ProposalException, IOException, InvalidArgumentException {
        List<CredentialResponse> credentialsList = new ArrayList<>();

        List<CredentialAsset> allCredentials = getCredentialAssetsByOwnerIdService.getCredentialsByOwnerId(ownerId);
        AccountAsset credentialRequester = getAccountAssetById(ownerId);

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
                SecretKey decryptedSymmetricKey =
                        SymmetricKeyCrypto.decryptKey(credentialAsset.getSymmetricKey(), credentialRequester);

                String heirsIdsString = SymmetricCrypto.decrypt(credentialAsset.getHeirsIds(), decryptedSymmetricKey)
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "");

                List<Long> heirsIds = heirsIdsString.length() > 0 ?
                        Arrays.stream(heirsIdsString.split(","))
                        .map(Long::parseLong).collect(Collectors.toList())
                        : new ArrayList<>();

                CredentialResponse responseCredential = new CredentialResponse(
                        credentialAsset.getCredentialId(),
                        SymmetricCrypto.decrypt(credentialAsset.getName(), decryptedSymmetricKey),
                        SymmetricCrypto.decrypt(credentialAsset.getDescription(), decryptedSymmetricKey),
                        SymmetricCrypto.decrypt(credentialAsset.getLink(), decryptedSymmetricKey),
                        SymmetricCrypto.decrypt(credentialAsset.getLogin(), decryptedSymmetricKey),
                        SymmetricCrypto.decrypt(credentialAsset.getPassword(), decryptedSymmetricKey),
                        credentialAsset.getCredentialOwnerId(),
                        heirsIds,
                        credentialAsset.getIsActive(),
                        credentialAsset.getCreatedAt(),
                        credentialAsset.getSymmetricKey()
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

    private AccountAsset getAccountAssetById(Long id) {
        try {
            return getAccountAssetByIdService.getUserAssetById(id);
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
