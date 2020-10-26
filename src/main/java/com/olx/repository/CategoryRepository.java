package com.olx.repository;

import com.olx.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Optional<Category> findByNameIgnoreCase(String name);
    public Optional<Category> findByNameIgnoreCaseAndParent (String name,Long parent);
}
