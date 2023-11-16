package com.app.tasktracker.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws_bucket_name}")
    private String bucketName;
    @Value("${aws_bucket_path}")
    private String bucketPath;
    private final S3Client s3Client;

    public Map<String, String>  upload(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest p = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentType("jpeg")
                .contentType("png")
                .contentType("ogg")
                .contentType("mp3")
                .contentType("mpeg")
                .contentType("ogg")
                .contentType("m4a")
                .contentType("oga")
                .contentType("pdf")
                .contentType("jpg")
                .contentLength(file.getSize())
                .key(key)
                .build();
        s3Client.putObject(p, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
       return Map.of("Link", bucketPath + key);

    }

    public Map<String, String> delete(String fileLink) {
        try {
            String key = fileLink.replace(bucketPath, "");
            s3Client.deleteObject(dor -> dor.bucket(bucketName).key(key).build());
        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return Map.of("message", fileLink + " has been deleted");
    }

    public List<String> listAllFiles(){
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Response listObjectsV2Result = s3Client.listObjectsV2(listObjectsRequest);
        return listObjectsV2Result.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }
}