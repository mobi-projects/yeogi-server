package com.example.yeogiserver.common.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String uploadImage(MultipartFile image){
        String originalFilename = image.getOriginalFilename();
        String newFilename = UUID.randomUUID() + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());
        try {
            amazonS3.putObject(bucketName , newFilename , image.getInputStream() , metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return amazonS3.getUrl(bucketName , newFilename).toString();
    }

    public void deleteImage(String imagePath) {
        if(imagePath == null || imagePath.isEmpty()) return;
        try {
            String[] urlParts = imagePath.split("/");

            amazonS3.deleteObject(bucketName , urlParts[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
