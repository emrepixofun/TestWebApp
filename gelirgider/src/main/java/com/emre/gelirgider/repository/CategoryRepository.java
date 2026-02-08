package com.emre.gelirgider.repository;

import com.emre.gelirgider.model.Category;
import com.emre.gelirgider.model.TransactionType;
import com.emre.gelirgider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    List<Category> findByUserAndType(User user, TransactionType type);
    Optional<Category> findByUserAndId(User user, Long id);
    boolean existsByUserAndId(User user, Long id);
}
