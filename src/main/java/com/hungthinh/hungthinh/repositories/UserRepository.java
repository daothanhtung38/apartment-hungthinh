package com.hungthinh.hungthinh.repositories;

import com.hungthinh.hungthinh.models.entities.bases.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String name);
    boolean existsByUsername(String username);
}
