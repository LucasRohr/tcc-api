package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InactivateFileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DeleteBucketFileService deleteBucketFileService;

    public void inactivate(Long id) {
        File file = fileRepository.findById(id).get();

        deleteBucketFileService.deleteFileFromS3Bucket(file.getBucketUrl(), file.getType().getValue().toLowerCase());

        file.setActive(false);
        fileRepository.save(file);
    }

}
