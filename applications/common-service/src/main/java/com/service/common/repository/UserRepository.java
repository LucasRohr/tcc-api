package com.service.common.repository;

import com.service.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.email = ?1 AND user.isActive = true")
    public User getUserByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.loginToken = ?1")
    public User validateUserLoginToken(String token);

}
