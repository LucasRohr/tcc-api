package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.dto.FileHeirDto;
import com.service.common.enums.FileTypeEnum;
import com.service.common.repository.FileHeirRepository;
import com.service.file.controller.response.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetHeirFilesService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

    @Autowired
    private GetFilesForAccountService getFilesForAccountService;

    public Page<FileResponse> getFiles(Pageable pageable, Long heirId, FileTypeEnum type) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                10,
                Sort.by(Sort.Direction.DESC, "updatedAt")
        );

        Page<File> files = fileHeirRepository.getHeirsFilesByType(pageRequest, heirId, type);

        return getFilesForAccountService.getFiles(pageRequest, files);
    }

    public List<FileHeirDto> getFiles(Long heirId) {
        List<FileHeir> files = fileHeirRepository.getHeirsFilesByHeir(heirId);
        ArrayList<FileHeirDto> filesHeirs = new ArrayList<>();
        files.forEach(file -> {
            filesHeirs.add(new FileHeirDto(
                    file.getId(),
                    file.getHeir().getId(),
                    file.getFile().getId(),
                    file.getFile().getName(),
                    file.getFile().getType()
            ));
        });
        return filesHeirs;
    }

}
