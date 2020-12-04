package com.service.file.service;

import com.service.file.controller.request.CreateFileRequest;
import com.service.file.controller.request.CreateMultipleFilesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SaveMultipleFilesService {

    @Autowired
    private SaveSingleFileService saveSingleFileService;

    public void saveFiles(List<MultipartFile> files, CreateMultipleFilesRequest createMultipleFilesRequest) {
        files.forEach(multipartFile -> {
            CreateFileRequest createFileRequest = new CreateFileRequest(
                    createMultipleFilesRequest.getOwnerId(),
                    multipartFile.getName(),
                    "",
                    createMultipleFilesRequest.getHeirsIds(),
                    createMultipleFilesRequest.getType()
            );

            saveSingleFileService.saveFile(multipartFile, createFileRequest);
        });
    }

}
