package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import com.service.common.repository.FileHeirRepository;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.OwnerRepository;
import com.service.file.controller.request.CreateFileRequest;
import com.service.common.repository.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void saveFile(MultipartFile file, CreateFileRequest createFileRequest) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String bucketUrl = uploadBucketFileService.uploadFile(
                file, createFileRequest.getType().toString().toLowerCase()
        );

        Owner owner = ownerRepository.findById(createFileRequest.getOwnerId()).get();

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

}
