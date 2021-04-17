package com.service.file.service;

import com.service.file.controller.request.CreateFileRequest;
import com.service.file.controller.request.CreateMultipleFilesRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveMultipleFilesService {

    @Autowired
    private SaveSingleFileService saveSingleFileService;

    public void saveFiles(MultipartFile[] files, CreateMultipleFilesRequest createMultipleFilesRequest)
            throws ProposalException, InvalidArgumentException {

        for(MultipartFile multipartFile : files) {
            CreateFileRequest createFileRequest = new CreateFileRequest(
                    createMultipleFilesRequest.getOwnerId(),
                    "Minha mídia",
                    "",
                    createMultipleFilesRequest.getHeirsIds(),
                    createMultipleFilesRequest.getType()
            );

            saveSingleFileService.saveFile(multipartFile, createFileRequest);
        }
    }

}
