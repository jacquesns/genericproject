package com.websystique.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websystique.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
