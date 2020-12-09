package com.service.file.controller;

import com.service.common.enums.FileTypeEnum;
import com.service.file.controller.request.CreateFileRequest;
import com.service.file.controller.request.CreateMultipleFilesRequest;
import com.service.file.controller.request.UpdateFileRequest;
import com.service.file.controller.response.FileHeirResponse;
import com.service.file.controller.response.FileResponse;
import com.service.file.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private SaveSingleFileService saveSingleFileService;

    @Autowired
    private SaveMultipleFilesService saveMultipleFilesService;

    @Autowired
    private InactivateFileService inactivateFileService;

    @Autowired
    private GetOwnerFilesService getOwnerFilesService;

    @Autowired
    private UpdateFileService updateFileService;

    @Autowired
    private GetOwnerHeirsForFileService getOwnerHeirsForFileService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "single-media-upload", consumes = {"multipart/form-data"})
    public void uploadSingleFile(
            @RequestPart(value = "file-content") MultipartFile file,
            @RequestPart(value = "file-info") CreateFileRequest createFileRequest
    ) {
        saveSingleFileService.saveFile(file, createFileRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "multiple-media-upload", consumes = {"multipart/form-data"})
    public void uploadSingleFile(
            @RequestPart(value = "file-content") MultipartFile[] files,
            @RequestPart(value = "file-info") CreateMultipleFilesRequest createMultipleFilesRequest
    ) {
        saveMultipleFilesService.saveFiles(files, createMultipleFilesRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("file-removal")
    public void deleteFile(@RequestParam("media_id") Long mediaId) {
        inactivateFileService.inactivate(mediaId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("file-update")
    public void updateFile(
            @RequestPart("file-content") MultipartFile file,
            @RequestPart("file-info") UpdateFileRequest updateFileRequest
    ) {
        updateFileService.updateFile(file, updateFileRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("file-owner-heirs")
    public List<FileHeirResponse> getOwnerHeirsForFile(
            @RequestParam("owner_id") Long ownerId
    ) {
        return getOwnerHeirsForFileService.getHeirs(ownerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("owner-files")
    public Page<FileResponse> getFiles(
            @RequestParam("page") int page,
            @RequestParam("owner_id") Long ownerId,
            @RequestParam("file_type") String type
    ) {
        Pageable pageable = PageRequest.of(page, 10);
       return getOwnerFilesService.getFiles(pageable, ownerId, FileTypeEnum.valueOf(type.toUpperCase()));
    }

}