package com.example.cms_shopping_cart.models;

import java.util.List;

import com.example.cms_shopping_cart.models.data.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findBySlug(String slug);
    Category findById(int id);
    List<Category> findAllByOrderBySortingAsc();
}
