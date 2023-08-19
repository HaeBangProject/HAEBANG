package com.haebang.haebang.configuration;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsS3Config {
    @Value("${cloud.aws.region.static}")
    private String region;
@Bean
public AmazonS3Client amazonS3Client() {
    return (AmazonS3Client) AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .enablePathStyleAccess()// "s3.{region}.amazonaws.com/{bucketname}/..."
            .build();
}
}