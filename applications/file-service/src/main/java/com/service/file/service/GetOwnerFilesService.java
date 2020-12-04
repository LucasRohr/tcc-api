package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.enums.FileTypeEnum;
import com.service.common.exceptions.CryptoException;
import com.service.file.controller.response.FileResponse;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetOwnerFilesService {

    @Autowired
    private GetBucketFileService getBucketFileService;

    @Autowired
    private FileRepository fileRepository;

    public Page<FileResponse> getFiles(Pageable pageable, Long ownerId, FileTypeEnum type) {
        List<FileResponse> fileResponses = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(), 10,
                Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<File> files = fileRepository.getOwnerFilesByType(pageRequest, ownerId, type.getValue());

        files.forEach(file -> {
            try {
                String fileBase = getBucketFileService.getFileFromBucket(
                        file.getBucketUrl(), file.getType().getValue().toLowerCase(), file.getMimeType()
                );

                String base64String = "data:application/" + file.getMimeType() + ";base64," + fileBase;

                FileResponse fileResponse = new FileResponse(
                        file.getId(),
                        file.getName(),
                        file.getDescription(),
                        file.getType(),
                        base64String
                );

                fileResponses.add(fileResponse);
            } catch (CryptoException | IOException e) {
                e.printStackTrace();
            }
        });

        return new PageImpl<FileResponse>(fileResponses, pageable, fileResponses.size());
    }

}
