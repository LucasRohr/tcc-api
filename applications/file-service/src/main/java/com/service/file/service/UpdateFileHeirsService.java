package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.domain.Heir;
import com.service.common.repository.FileHeirRepository;
import com.service.common.repository.HeirRepository;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdateFileHeirsService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

    @Autowired
    private HeirRepository heirRepository;

    @Autowired
    private FileRepository fileRepository;

    public void updateHeirs(Long fileId, List<Long> selectedHeirsIds) {
        List<FileHeir> fileHeirs = fileHeirRepository.getFilesHeirsByFile(fileId);

        List<Long> heirsWithFileIds =
                fileHeirs.stream().map(fileHeir -> fileHeir.getHeir().getId()).collect(Collectors.toList());

        if(fileHeirs.size() > 0) {
            fileHeirs.forEach(fileHeir -> {
                boolean isHeirToRemove =
                        !selectedHeirsIds.contains(fileHeir.getHeir().getId());

                if(isHeirToRemove) {
                    fileHeirRepository.delete(fileHeir);
                }
            });

            selectedHeirsIds.forEach(heirId -> {
                boolean isNewHeir = !heirsWithFileIds.contains(heirId);

                if(isNewHeir) {
                    saveNewFileHeir(fileId, heirId);
                }
            });
        }
    }

    private void saveNewFileHeir(Long fileId, Long heirId) {
        File file = fileRepository.findById(fileId).get();
        Heir heir = heirRepository.findById(heirId).get();

        FileHeir fileHeir = new FileHeir(heir, file);

        fileHeirRepository.save(fileHeir);
    }

    public void unlinkFileHeirs(Long heirId, List<Long> fileHeirIds) {
        fileHeirIds.forEach(id -> {
            FileHeir fh = fileHeirRepository.getFileHeirByFileAndHeir(heirId, id);
            fileHeirRepository.delete(fh);
        });
    }

}
