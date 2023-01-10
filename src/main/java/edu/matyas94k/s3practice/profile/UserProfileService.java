package edu.matyas94k.s3practice.profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import edu.matyas94k.s3practice.bucket.BucketEnum;
import edu.matyas94k.s3practice.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        checkFileEmpty(file);
        checkImageFile(file);
        final UserProfile userProfile = extractUserProfileOrThrow(userProfileId);
        final Map<String, String> metadata = extractMetadata(file);
        try {
            final String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            final int extensionPosition = originalFilename.lastIndexOf('.');
            final String key = String.format("%s-%s/%s-%s%s", userProfile.getUsername(), userProfileId, originalFilename.substring(0, extensionPosition),
                    UUID.randomUUID(), originalFilename.substring(extensionPosition));
            final String s3ObjectKey = fileStore.save(BucketEnum.PROFILE_IMAGE.getBucketName(), key, file.getInputStream(), Optional.of(metadata));
            System.out.println(s3ObjectKey);
            // Update DB entry of the user with S3 key
            userProfile.setUserProfileImageLink(s3ObjectKey);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        final UserProfile userProfile = extractUserProfileOrThrow(userProfileId);
        return userProfile.getUserProfileImageLink().map(fileStore::download).orElse(new byte[0]);
    }

    private void checkFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot upload empty file [%d]", file.getSize()));
        }
    }

    private void checkImageFile(MultipartFile file) {
        final List<String> imageFormats = List.of(MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);
        if (!imageFormats.contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }
    }

    private UserProfile extractUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService.getUserProfiles().stream()
                .filter(up -> up.getUserProfileId().equals(userProfileId)).findFirst().orElseThrow(() ->
                        new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        final Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

}
