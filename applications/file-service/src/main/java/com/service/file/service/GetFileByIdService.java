package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.exceptions.CryptoException;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GetFileByIdService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private GetBucketFileService getBucketFileService;

    public String getfile(Long id) throws IOException, CryptoException {
        File file = fileRepository.findById(id).get();

        String fileBase = getBucketFileService.getFileFromBucket(
                file.getBucketUrl(),
                file.getType().toString().toLowerCase(),
                file.getMimeType()
        );

        return "data:application/" + file.getMimeType() + ";base64," + fileBase;
    }

}
