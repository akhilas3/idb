package com.intellor.idb.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AwsS3ClientConfig {

  @Value("${cloud.aws.credentials.accessKey:null}")
  private String awsId;

  @Value("${cloud.aws.credentials.secretKey:null}")
  private String awsKey;

//  @Value("${cloud.aws.region.static}")
//  private String region;

  @Bean
  @Primary
  public AmazonS3 s3client() {
    AmazonS3 amazonS3Client;
    if(awsId !=null && awsKey !=null) {
      BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsId, awsKey);
      amazonS3Client = AmazonS3ClientBuilder.standard()
              .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
              .withForceGlobalBucketAccessEnabled(true)
              .build();
    }else{
      amazonS3Client = AmazonS3ClientBuilder.standard()
              .withForceGlobalBucketAccessEnabled(true)
              .build();
    }

    return amazonS3Client;
  }
}