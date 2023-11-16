package com.app.tasktracker.config.s3Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3FileConfig {

    @Value("${aws_access_key}")
    private String AWS_ACCESS_KEY;
    @Value("${aws_secret_key}")
    private String AWS_SECRET_KEY;
    @Value("${aws_region}")
    private String REGION;

    @Bean
    S3Client s3Client() {
        Region region = Region.of(REGION);
        final AwsBasicCredentials credentials = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}