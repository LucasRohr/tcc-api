package com.service.file.service;

import com.service.common.domain.FileHeir;
import com.service.common.repository.FileHeirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnlinkFileHeirsService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

    public void unlinkFileHeirs(Long heirId, List<Long> fileHeirIds) {
        fileHeirIds.forEach(id -> {
            FileHeir fh = fileHeirRepository.getFileHeirByFileAndHeir(heirId, id);
            fileHeirRepository.delete(fh);
        });
    }

}
