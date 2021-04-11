package com.service.file.service;

import com.service.common.crypto.SymmetricCrypto;
import com.service.common.crypto.SymmetricKeyCrypto;
import com.service.common.domain.*;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.domain.fabric.file.FileRecordModel;
import com.service.common.repository.FileHeirRepository;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.OwnerRepository;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
import com.service.common.service.fabric.file.SaveFileAssetService;
import com.service.file.controller.request.CreateFileRequest;
import com.service.common.repository.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SaveSingleFileService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UploadBucketFileService uploadBucketFileService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    @Autowired
    private SaveFileAssetService saveFileAssetService;

    public void saveFile(MultipartFile file, CreateFileRequest createFileRequest)
            throws InvalidArgumentException, ProposalException, InvalidKeySpecException, NoSuchAlgorithmException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Owner owner = ownerRepository.findById(createFileRequest.getOwnerId()).get();
        AccountAsset ownerAsset = getAccountAssetById(owner.getId());

        List<AccountAsset> accountAssets = new ArrayList<>();
        accountAssets.add(ownerAsset);

        createFileRequest.getHeirsIds().forEach(heirId -> {
            AccountAsset accountAsset = getAccountAssetById(heirId);
            accountAssets.add(accountAsset);
        });

        SecretKey fileKey = SymmetricCrypto.generateKey("password");

        String bucketUrl = uploadBucketFileService.uploadFile(
                file, createFileRequest.getType().toString().toLowerCase(), fileKey
        );

        File fileToSave = new File(
                createFileRequest.getName(),
                createFileRequest.getDescription(),
                bucketUrl,
                createFileRequest.getType(),
                extension,
                LocalDateTime.now(),
                LocalDateTime.now(),
                owner
        );

        File savedFile = fileRepository.save(fileToSave);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        Long timestamp = zonedDateTime.toInstant().toEpochMilli();

        String encryptedSymmetricKey = SymmetricKeyCrypto.encryptKey(fileKey.getEncoded(), accountAssets);

        FileRecordModel fileRecordModel = new FileRecordModel(
                savedFile.getId(),
                encryptedSymmetricKey,
                timestamp,
                owner.getAccount().getId()
        );

        saveFileAssetService.createTransaction(fileRecordModel);

        createFileRequest.getHeirsIds().forEach(heirId -> {
            Heir heir = heirRepository.findById(heirId).get();

            FileHeir fileHeir = new FileHeir(
                    heir,
                    savedFile
            );

            fileHeirRepository.save(fileHeir);
        });
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
