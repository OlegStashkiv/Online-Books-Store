package com.olegstashkiv.booksstore.repository.category;

import com.olegstashkiv.booksstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
