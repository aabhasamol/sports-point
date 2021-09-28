package com.example.cms_shopping_cart.models;


import java.util.List;

import com.example.cms_shopping_cart.models.data.page;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PageRepository extends JpaRepository<page, Integer> 
{
    page findBySlug(String slug);
    page findBySlugAndIdNot(String slug, int id);
    page findById(int id);
    List<page> findAllByOrderBySortingAsc();
}
