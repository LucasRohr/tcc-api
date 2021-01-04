package com.service.user.service;

import com.service.common.domain.Heir;
import com.service.common.dto.CredentialResponseWithouPassword;
import com.service.common.dto.FileHeirDto;
import com.service.common.dto.HeirAssetCheckDto;
import com.service.common.dto.HeirCredentialAssetCheckDto;
import com.service.common.repository.HeirRepository;
import com.service.user.clients.CredentialClient;
import com.service.user.clients.FileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCheckAssetsByHeirService {

    @Autowired
    private CredentialClient credentialClient;

    @Autowired
    private FileClient fileClient;

    @Autowired
    private HeirRepository heirRepository;

    public List<HeirAssetCheckDto> getCheckAssetsByHeir(Long heirId) {

        Heir heir = heirRepository.getHeirByAccountId(heirId);
        List<CredentialResponseWithouPassword> creds = credentialClient.getCredentialsByHeirsOwner(heir.getOwner().getAccount().getId());
        List<FileHeirDto> fileHeirs = fileClient.getAllFilesByHeir(heirId);
        ArrayList<HeirAssetCheckDto> heirAssetChecks = new ArrayList<>();

        creds.forEach(cred -> {
            if (cred.getHeirsIds().contains(heirId) && cred.getIsActive()) {
                heirAssetChecks.add(new HeirCredentialAssetCheckDto(
                        cred.getCredentialId(),
                        cred.getName(),
                        cred.getHeirsIds()
                ));
            }
        });

        fileHeirs.forEach(fileHeir -> {
            heirAssetChecks.add(new HeirAssetCheckDto(
                    fileHeir.getId(),
                    fileHeir.getType().name(),
                    fileHeir.getFileName()
            ));
        });

        return heirAssetChecks;
    }
}
