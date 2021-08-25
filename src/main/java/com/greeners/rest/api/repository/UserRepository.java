package com.greeners.rest.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
}