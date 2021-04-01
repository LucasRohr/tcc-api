package com.service.file.service;

import com.service.common.crypto.SymmetricCrypto;
import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.common.domain.fabric.account.AccountAsset;
import com.service.common.repository.FileHeirRepository;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.OwnerRepository;
import com.service.common.service.fabric.account.GetAccountAssetByIdService;
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
import java.time.LocalDateTime;

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
    private SymmetricCrypto symmetricCrypto;

    @Autowired
    private GetAccountAssetByIdService getAccountAssetByIdService;

    public void saveFile(MultipartFile file, CreateFileRequest createFileRequest) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Owner owner = ownerRepository.findById(createFileRequest.getOwnerId()).get();
        AccountAsset ownerAsset = getOwnerAssetById(owner.getId());

        SecretKey fileKey = symmetricCrypto.generateKey(ownerAsset.getCryptoPassword());

        String bucketUrl = uploadBucketFileService.uploadFile(
                file, createFileRequest.getType().toString().toLowerCase()
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

        createFileRequest.getHeirsIds().forEach(heirId -> {
            Heir heir = heirRepository.findById(heirId).get();

            FileHeir fileHeir = new FileHeir(
                    heir,
                    savedFile
            );

            fileHeirRepository.save(fileHeir);
        });
    }

    private AccountAsset getOwnerAssetById(Long id) {
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
