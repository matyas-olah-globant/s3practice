package edu.matyas94k.s3practice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsUtils {
    private static AWSCredentials awsCredentials;

    private AwsUtils() {}

    public static void configure(final String accessKey, final String secretKey) {
        if (awsCredentials == null) {
            awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        }
    }

    public static AmazonS3 s3() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

}
