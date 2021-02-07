package com.service.file.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.service.common.exceptions.CryptoException;
import com.service.common.helpers.CryptoUtils;
import com.service.common.helpers.RandomCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class UploadBucketFileService {
    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    private static final int FILE_NAME_LENGTH = 20;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return convFile;
    }

    private String generateFileName(File file) {
        return new Date().getTime() + "-" + file.getName().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.BucketOwnerFullControl));
    }

    private File encryptFile(File file) throws CryptoException {
        //TO DO: use blockchain user private key
        String privateKey = "some private key";
        String randomFileName = new RandomCode(FILE_NAME_LENGTH).nextString();

        String filePath = System.getenv("FILE_PATH") + randomFileName + ".enc";

        File outputFile = new File(filePath);
        CryptoUtils.encrypt(privateKey, file, outputFile);
        return outputFile;
    }

    public String uploadFile(MultipartFile multipartFile, String fileFolder) {
        String fileUrl = "";

        try {
            File file = convertMultiPartToFile(multipartFile);
            File encryptFile = encryptFile(file);

            String fileName = fileFolder + "/" + generateFileName(encryptFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, encryptFile);

            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUrl;
    }
}
