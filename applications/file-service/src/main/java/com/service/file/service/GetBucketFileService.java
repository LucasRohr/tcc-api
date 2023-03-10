package com.service.file.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.service.common.exceptions.CryptoException;
import com.service.common.helpers.CryptoUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class GetBucketFileService {
    private AmazonS3 s3client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private S3Object getFileFromS3bucket(String fileName) {
        return s3client.getObject(new GetObjectRequest(bucketName, fileName));
    }

    private String convertFileToBase64(File file) throws IOException {
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.US_ASCII);
    }

    public String getFileFromBucket(String fileUrl, String folderName, String fileExtension, SecretKey symmetricKey)
            throws IOException, CryptoException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String pureFileName = fileName.substring(0, fileName.lastIndexOf("."));
        File outputFile = new File(System.getenv("FILE_PATH") + pureFileName + fileExtension);

        S3ObjectInputStream inputStream = getFileFromS3bucket(folderName + "/" + fileName).getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, outputFile);
        File inputFile = FileUtils.getFile(outputFile);

        CryptoUtils.decrypt(symmetricKey, inputFile, outputFile);

        return convertFileToBase64(outputFile);
    }
}
