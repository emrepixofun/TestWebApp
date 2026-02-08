package com.emre.gelirgider.repository;

import com.emre.gelirgider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("gelirgiderUserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);
}
