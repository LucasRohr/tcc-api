package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.domain.fabric.user.UserAsset;
import com.service.common.dto.FileHeirDto;
import com.service.common.enums.FileTypeEnum;
import com.service.common.repository.FileHeirRepository;
import com.service.file.controller.response.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetHeirFilesService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

    @Autowired
    private GetFilesForAccountService getFilesForAccountService;

    public Page<FileResponse> getFiles(Pageable pageable, Long heirId, FileTypeEnum type) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                10
        );

        Page<FileHeir> fileHeirs = fileHeirRepository.getHeirsFilesByType(pageRequest, heirId, type);

        List<File> pageFiles = fileHeirs.getContent().stream()
                .map(fileHeir -> fileHeir.getFile()).collect(Collectors.toList());

        pageFiles.sort(
                new Comparator<File>() {
                    @Override
                    public int compare(File fileA, File fileB) {
                        return fileA.getUpdatedAt().compareTo(fileB.getUpdatedAt());
                    }
                }
        );

        Page<File> files = new PageImpl<File>(pageFiles, pageable, fileHeirs.getTotalElements());

        return getFilesForAccountService.getFiles(pageRequest, files);
    }

}
