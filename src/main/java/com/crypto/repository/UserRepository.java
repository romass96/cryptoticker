package com.crypto.repository;

import com.crypto.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.enabled = :enabled where u.id = :user_id")
    int updateUserEnabled(@Param("user_id") Long userId, @Param("enabled") boolean enabled);
}
