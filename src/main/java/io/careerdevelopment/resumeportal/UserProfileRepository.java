package io.careerdevelopment.resumeportal;
import io.careerdevelopment.resumeportal.models.UserProfile;
import io.careerdevelopment.resumeportal.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByUserName(String userName);//jpa returns the user name info
}
