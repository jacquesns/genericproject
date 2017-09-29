package com.websystique.api.services;

import java.util.List;

import com.websystique.api.model.Order;


public interface OrderService {
	Order findById(Long id);

	void createOrder(Order order);

	Order updateOrder(Order order);

	void deleteOrderById(Long id);

	void deleteAllOrders();

	List<Order> findAllOrders();

}
