package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.UserRegistrationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistrationData, Integer> {
    @Query(value = "select * from userregistration_db where email_id = :emailId", nativeQuery = true)
    UserRegistrationData getUserByEmailId(String emailId);

    @Query(value = "select * from userregistration_db where email_id = :emailId and password = :password", nativeQuery = true)
    Optional<UserRegistrationData> findByEmailIdAndPassword(String emailId, String password);
}
