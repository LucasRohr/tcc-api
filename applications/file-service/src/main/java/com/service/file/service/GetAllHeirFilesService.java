package com.service.file.service;

import com.service.common.domain.FileHeir;
import com.service.common.dto.FileHeirDto;
import com.service.common.repository.FileHeirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllHeirFilesService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

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
