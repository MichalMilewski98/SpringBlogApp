package com.example.blog.repositories;

import com.example.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
        @Query(value="select u from Users u where u.username = ?1", nativeQuery = true)
        User findUserByUsername(String username);
        Optional<User> findByEmail(String email);

}

