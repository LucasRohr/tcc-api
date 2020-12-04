package com.service.file.service;

import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.domain.Heir;
import com.service.common.repository.FileHeirRepository;
import com.service.common.repository.HeirRepository;
import com.service.file.controller.request.UpdateFileHeirsRequest;
import com.service.common.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateHeirs(UpdateFileHeirsRequest updateFileHeirsRequest) {
        List<FileHeir> fileHeirs = fileHeirRepository.getFilesHeirsByFile(updateFileHeirsRequest.getId());

        List<Long> heirsWithFileIds =
                fileHeirs.stream().map(fileHeir -> fileHeir.getHeir().getId()).collect(Collectors.toList());

        if(fileHeirs.size() > 0) {
            fileHeirs.forEach(fileHeir -> {
                boolean isHeirToRemove =
                        !updateFileHeirsRequest.getSelectedHeirsIds().contains(fileHeir.getHeir().getId());

                if(isHeirToRemove) {
                    heirRepository.delete(fileHeir.getHeir());
                }
            });

            updateFileHeirsRequest.getSelectedHeirsIds().forEach(heirId -> {
                boolean isNewHeir = !heirsWithFileIds.contains(heirId);

                if(isNewHeir) {
                    saveNewFileHeir(updateFileHeirsRequest.getId(), heirId);
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

}
