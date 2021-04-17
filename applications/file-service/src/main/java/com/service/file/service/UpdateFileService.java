package com.service.file.service;

import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.credential.CredentialAsset;
import com.service.common.domain.fabric.file.FileAsset;
import com.service.common.domain.fabric.file.FileRecordModel;
import com.service.common.repository.FileHeirRepository;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.common.service.fabric.file.GetFileAssetByIdService;
import com.service.common.service.fabric.file.SaveFileAssetService;
import com.service.file.controller.request.UpdateFileRequest;
import com.service.common.repository.FileRepository;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdateFileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DeleteBucketFileService deleteBucketFileService;

    @Autowired
    private UploadBucketFileService uploadBucketFileService;

    @Autowired
    private UpdateFileHeirsService updateFileHeirsService;

    @Autowired
    private GetFileAssetByIdService getFileAssetByIdService;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    @Autowired
    private FileHeirRepository fileHeirRepository;

    @Autowired
    private SaveFileAssetService saveFileAssetService;

    public void updateFile(MultipartFile editFile, UpdateFileRequest updateFileRequest)
            throws ProposalException, IOException, InvalidArgumentException {
        File file = fileRepository.findById(updateFileRequest.getId()).get();
        String fileFolder = file.getType().toString().toLowerCase();

        List<FileHeir> fileHeirs = fileHeirRepository.getFilesHeirsByFile(updateFileRequest.getId());

        List<Long> heirsIds = fileHeirs.stream().map(fileHeir -> {
            return fileHeir.getHeir().getAccount().getId();
        }).collect(Collectors.toList());

        List<FileAsset> fileAssets = getFileAssetByIdService.getFileAssetById(updateFileRequest.getId());

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

        AccountAsset accountAsset = getAccountAssetByIdService.getUserAssetById(updateFileRequest.getOwnerId());

        deleteBucketFileService.deleteFileFromS3Bucket(
                file.getBucketUrl(),
                fileFolder
        );

        boolean hasChangedHeirs = !heirsIds.toString().equals(updateFileRequest.getHeirsIds().toString());

        if (hasChangedHeirs) {
            String encryptedSymmetricKeyToSave = fileAsset.getSymmetricKey();

            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
            Long timestamp = zonedDateTime.toInstant().toEpochMilli();

            FileRecordModel fileRecordModel = new FileRecordModel(
                    updateFileRequest.getId(),
                    encryptedSymmetricKeyToSave,
                    timestamp,
                    updateFileRequest.getOwnerId()
            );

            saveFileAssetService.createTransaction(fileRecordModel);
        }

        String encryptedSymmetricKey = fileAsset.getSymmetricKey();
        SecretKey decryptedSymmetricKey = SymmetricKeyCrypto.decryptKey(encryptedSymmetricKey, accountAsset);

        String bucketUrl = uploadBucketFileService.uploadFile(editFile, fileFolder, decryptedSymmetricKey);

        file.setName(updateFileRequest.getName());
        file.setDescription(updateFileRequest.getDescription());
        file.setBucketUrl(bucketUrl);

        fileRepository.save(file);

        updateFileHeirsService.updateHeirs(file.getId(), updateFileRequest.getHeirsIds());
    }


}
