package edu.matyas94k.s3practice.bucket;

public enum BucketEnum {
    PROFILE_IMAGE("image-upload-matyas94k");

    private final String bucketName;

    BucketEnum(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

}
