package com.service.file.service;

import com.service.common.domain.File;
import com.service.file.controller.request.UpdateFileRequest;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void updateFile(MultipartFile editFile, UpdateFileRequest updateFileRequest) {
        File file = fileRepository.findById(updateFileRequest.getId()).get();
        String fileFolder = file.getType().toString().toLowerCase();

        deleteBucketFileService.deleteFileFromS3Bucket(
                file.getBucketUrl(),
                fileFolder
        );

        String bucketUrl = uploadBucketFileService.uploadFile(editFile, fileFolder);

        file.setName(updateFileRequest.getName());
        file.setDescription(updateFileRequest.getDescription());
        file.setBucketUrl(bucketUrl);

        fileRepository.save(file);

        updateFileHeirsService.updateHeirs(file.getId(), updateFileRequest.getHeirsIds());
    }

}
