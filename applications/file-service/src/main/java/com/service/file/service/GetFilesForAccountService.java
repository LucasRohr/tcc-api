package com.service.file.service;

import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.File;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.file.FileAsset;
import com.service.common.enums.FileTypeEnum;
import com.service.common.exceptions.CryptoException;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.common.service.fabric.file.GetFileAssetByIdService;
import com.service.file.controller.response.FileResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GetFilesForAccountService {

    @Autowired
    private GetBucketFileService getBucketFileService;

    @Autowired
    private GetFileAssetByIdService getFileAssetByIdService;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    public Page<FileResponse> getFiles(Pageable pageable, Page<File> files) {
        List<FileResponse> fileResponses = new ArrayList<>();

        files.getContent().forEach(file -> {
            try {
                String fileBase = "";
                boolean isNotVideoFile = file.getType() != FileTypeEnum.VIDEO;

                if(isNotVideoFile) {
                    fileBase = getBucketFileService.getFileFromBucket(
                            file.getBucketUrl(),
                            file.getType().toString().toLowerCase(),
                            file.getMimeType(),
                            getFileSymmetricKey(file)
                    );
                }

                String base64String =
                        isNotVideoFile ? ("data:application/" + file.getMimeType() + ";base64," + fileBase) : "";

                FileResponse fileResponse = new com.service.file.controller.response.FileResponse(
                        file.getId(),
                        file.getName(),
                        file.getDescription(),
                        file.getType(),
                        base64String,
                        file.getMimeType()
                );

                fileResponses.add(fileResponse);
            } catch (CryptoException | IOException | ProposalException | InvalidArgumentException e) {
                e.printStackTrace();
            }
        });

        return new PageImpl<FileResponse>(fileResponses, pageable, files.getTotalElements());
    }

    private SecretKey getFileSymmetricKey(File file) throws ProposalException, IOException, InvalidArgumentException {
        List<FileAsset> fileAssets = getFileAssetByIdService.getFileAssetById(file.getId());
        AccountAsset fileRequester = getAccountAssetByIdService.getUserAssetById(file.getOwner().getAccount().getId());

        fileAssets.sort(
                new Comparator<FileAsset>() {
                    @Override
                    public int compare(FileAsset fileA, FileAsset fileB) {
                        Long createdAtA = fileA.getCreatedAt();
                        Long createdAtB = fileB.getCreatedAt();

                        return createdAtA > createdAtB ? -1 : 1;
                    }
                }
        );

        FileAsset fileAsset = fileAssets.get(0);

        return SymmetricKeyCrypto.decryptKey(fileAsset.getSymmetricKey(), fileRequester);
    }

}
