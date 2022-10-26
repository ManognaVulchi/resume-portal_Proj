package io.careerdevelopment.resumeportal;
import io.careerdevelopment.resumeportal.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserName(String userName);//jpa returns the user name info
}
