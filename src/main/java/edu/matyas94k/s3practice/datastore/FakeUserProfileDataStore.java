package edu.matyas94k.s3practice.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import edu.matyas94k.s3practice.profile.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("2652aaa8-240e-4e0b-b8b5-c5ad989e9352"), "JanetJones", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("ea17f03b-b2ab-44a7-8045-b7f0358f546b"), "AntonioJunior", null));
    }

    public static List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }

}
