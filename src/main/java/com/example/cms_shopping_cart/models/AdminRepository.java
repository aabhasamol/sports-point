package com.example.cms_shopping_cart.models;

import com.example.cms_shopping_cart.models.data.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);
    
}
