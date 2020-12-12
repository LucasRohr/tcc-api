package com.service.file.service;

import com.service.common.domain.FileHeir;
import com.service.common.domain.Heir;
import com.service.common.repository.FileHeirRepository;
import com.service.common.repository.HeirRepository;
import com.service.file.controller.response.FileHeirResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetOwnerHeirsForFileService {

    @Autowired
    private FileHeirRepository fileHeirRepository;

    @Autowired
    private HeirRepository heirRepository;

    public List<FileHeirResponse> getHeirs(Long ownerId) {
        List<Heir> allOwnerHeirs = heirRepository.getAllOwnerHeirs(ownerId);
        List<FileHeir> fileHeirs = fileHeirRepository.getFilesHeirsByOwner(ownerId);

        List<Long> fileHeirsIds =
                fileHeirs.stream().map(fileHeir -> fileHeir.getHeir().getId()).collect(Collectors.toList());

        List<FileHeirResponse> fileHeirResponses = new ArrayList<>();

        allOwnerHeirs.forEach(heir -> {
            boolean heirHasFile = fileHeirsIds.contains(heir.getId());

            FileHeirResponse fileHeirResponse = new FileHeirResponse(
                    heir.getId(),
                    heir.getAccount().getUser().getName(),
                    heir.getAccount().getName(),
                    heir.getAccount().getUser().getEmail(),
                    heirHasFile
            );

            fileHeirResponses.add(fileHeirResponse);
        });

        fileHeirResponses.sort(
                new Comparator<FileHeirResponse>() {
                    @Override
                    public int compare(FileHeirResponse heirA, FileHeirResponse heirB) {
                        return heirA.isHasItem() ? -1 : 1;
                    }
                }
        );

        return fileHeirResponses;
    }
}
