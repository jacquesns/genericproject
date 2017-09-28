package com.websystique.api.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.api.controllers.common.EntityNotFoundException;
import com.websystique.api.model.Order;
import com.websystique.api.repositories.OrderRepository;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService{
	public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Order findById(Long id) {
		return orderRepository.findOne(id);
	}

	@Override
	public void createOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Order updateOrder(Order order){
		Order currentOrder = findById(order.getId());		
		if (currentOrder == null) {
			logger.error("Unable to update. Order with id {} not found.", order.getId());
			throw new EntityNotFoundException("Unable to upate. Order with id " + order.getId() + " not found.");
		}
		currentOrder.setProduct(order.getProduct());
		currentOrder.setStatus(order.getStatus());
		currentOrder.setTotalValue(order.getTotalValue());
		currentOrder.setUser(order.getUser());
		return orderRepository.save(order);
	}
	
	@Override
	public void deleteOrderById(Long id){
		Order order = findById(id);
		if (order == null) {
			logger.error("Unable to delete. Order with id {} not found.", id);
			throw new EntityNotFoundException("Unable to delete. Order with id " + id + " not found.");
		}
		orderRepository.delete(id);
	}

	@Override
	public void deleteAllOrders(){
		orderRepository.deleteAll();
	}

	@Override
	public List<Order> findAllOrders(){
		return orderRepository.findAll();
	}
	

}