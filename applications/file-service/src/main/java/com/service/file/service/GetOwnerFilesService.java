package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.enums.FileTypeEnum;
import com.service.file.controller.response.FileResponse;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class GetOwnerFilesService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private GetFilesForAccountService getFilesForAccountService;

    public Page<FileResponse> getFiles(Pageable pageable, Long ownerId, FileTypeEnum type) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                10,
                Sort.by(Sort.Direction.DESC, "updatedAt")
        );

        Page<File> files = fileRepository.getOwnerFilesByType(pageRequest, ownerId, type);

        return getFilesForAccountService.getFiles(pageRequest, files);
    }

}
