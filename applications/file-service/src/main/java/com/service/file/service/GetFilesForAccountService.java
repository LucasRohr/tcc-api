package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.enums.FileTypeEnum;
import com.service.common.exceptions.CryptoException;
import com.service.file.controller.response.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetFilesForAccountService {

    @Autowired
    private GetBucketFileService getBucketFileService;

    public Page<FileResponse> getFiles(Pageable pageable, Page<File> files) {
        List<FileResponse> fileResponses = new ArrayList<>();

        files.getContent().forEach(file -> {
            try {
                String fileBase = "";
                boolean isNotVideoFile = file.getType() != FileTypeEnum.VIDEO;

                if(isNotVideoFile) {
                    fileBase = getBucketFileService.getFileFromBucket(
                            file.getBucketUrl(), file.getType().toString().toLowerCase(), file.getMimeType()
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
            } catch (CryptoException | IOException e) {
                e.printStackTrace();
            }
        });

        return new PageImpl<FileResponse>(fileResponses, pageable, files.getTotalElements());
    }

}
