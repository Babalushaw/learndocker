package com.assignment.repository;

import com.assignment.model.Role;
import com.assignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   @Query("SELECT user FROM User user WHERE user.username = :username")
   Optional<User> findByEmail(@Param("username") String username);
   @Query("SELECT admin FROM User admin WHERE admin.role = :role")
   User findAdmin(@Param("role")String role);
}
