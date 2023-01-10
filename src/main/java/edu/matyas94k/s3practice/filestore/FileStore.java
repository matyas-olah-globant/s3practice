package edu.matyas94k.s3practice.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import edu.matyas94k.s3practice.bucket.BucketEnum;
import edu.matyas94k.s3practice.config.AwsUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {
    private AmazonS3 s3;

    private AmazonS3 getS3() {
        if (s3 == null) {
            s3 = AwsUtils.s3();
        }
        return s3;
    }

    public String save(String path, String fileName, InputStream inputStream, Optional<Map<String, String>> optionalMetadata) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> map.forEach(metadata::addUserMetadata));
        try {
            getS3().putObject(path, fileName, inputStream, metadata);
            return getS3().getObject(path, fileName).getKey();
        } catch (SdkClientException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException("Failed to store file to S3", e);
        }
    }

    public byte[] download(String key) {
        try {
            return IOUtils.toByteArray(getS3().getObject(BucketEnum.PROFILE_IMAGE.getBucketName(), key).getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download");
        }
    }

}
