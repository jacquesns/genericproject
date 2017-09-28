package com.websystique.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websystique.api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
