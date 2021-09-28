package com.example.cms_shopping_cart.models;

import com.example.cms_shopping_cart.models.data.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);
    
}
