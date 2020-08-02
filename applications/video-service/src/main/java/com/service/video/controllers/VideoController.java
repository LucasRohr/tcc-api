package com.service.video.controllers;

import com.service.common.service.DeleteBucketFile;
import com.service.common.service.UploadBucketFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private UploadBucketFile uploadBucketFile;

    @Autowired
    private DeleteBucketFile deleteBucketFile;

    @PostMapping("upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.uploadBucketFile.uploadFile(file, "videos");
    }

    @DeleteMapping("delete")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.deleteBucketFile.deleteFileFromS3Bucket(fileUrl, "videos");
    }
}
