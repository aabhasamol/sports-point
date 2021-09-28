package com.example.cms_shopping_cart.models;

import java.util.List;

import com.example.cms_shopping_cart.models.data.Products;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Integer>
{
    Products findBySlug(String slug);

    Products findById(int id);

    Products findBySlugAndIdNot(String slug, int id);
    
    Page<Products> findAll(Pageable pagable);

    List<Products> findAllByCategoryId(String categoryId, Pageable pageable);

    long countByCategoryId(String categoryId);
}
