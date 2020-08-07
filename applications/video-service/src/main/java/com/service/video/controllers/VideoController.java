package com.service.video.controllers;

import com.service.common.exceptions.CryptoException;
import com.service.common.service.DeleteBucketFile;
import com.service.common.service.GetBucketFile;
import com.service.common.service.UploadBucketFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private UploadBucketFile uploadBucketFile;

    @Autowired
    private DeleteBucketFile deleteBucketFile;

    @Autowired
    private GetBucketFile getBucketFile;

    @PostMapping("upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.uploadBucketFile.uploadFile(file, "videos");
    }

    @DeleteMapping("delete")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.deleteBucketFile.deleteFileFromS3Bucket(fileUrl, "videos");
    }

    @GetMapping("get/{ownerId}")
    public String getFile(@PathVariable("ownerId") Long ownerId) throws IOException, CryptoException {
        // TO DO: call getFileFromBucket from service, returning a list of files to the front
        return this.getBucketFile.getFileFromBucket(
                "https://tcc-douglas-lucas.s3-sa-east-1.amazonaws.com/videos/1596489796685-banner_final.enc", "videos", "mp4"
        );
    }
}
