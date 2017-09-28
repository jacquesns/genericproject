package com.websystique.api.controllers;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.websystique.api.controllers.common.EntityNotFoundException;
import com.websystique.api.model.Order;
import com.websystique.api.services.OrderService;

@Controller()
public class OrderController {
	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	/**
	 * Retrieve All Orders 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/orders", method = RequestMethod.GET)
	public ResponseEntity<?> listAllOrders() {
		logger.info("Fetching all orders");
		List<Order> orders = orderService.findAllOrders();
		if (orders.isEmpty()) {
			logger.info("No orders found, returning http status code " + HttpStatus.NO_CONTENT);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	/**
	 * Retrieve Single Order 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/orders/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrder(@PathVariable("id") long id) {
		logger.info("Fetching Order with id {}", id);
		Order order = orderService.findById(id);
		if (order == null) {
			logger.error("Order with id {} not found.", id);
			throw new EntityNotFoundException(String.format("Order with id %d not found.", id));
		}
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	/**
	 * Create a Order 
	 * @param order
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/api/orders", method = RequestMethod.POST)
	public ResponseEntity<?> createOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Order : {}", order);
		orderService.createOrder(order);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/order/{id}").buildAndExpand(order.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update a Order
	 * @param id
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/api/orders/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
		logger.info("Updating Order with id {}", id);
		Order updatedOrder = orderService.updateOrder(order);
		return new ResponseEntity<Order>(updatedOrder, HttpStatus.OK);
	}

	/**
	 * Delete order with id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/orders/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Order with id {}", id);
		orderService.deleteOrderById(id);
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Delete All Orders 
	 * @return
	 */
	@RequestMapping(value = "api/orders", method = RequestMethod.DELETE)
	public ResponseEntity<Order> deleteAllOrders() {
		logger.info("Deleting All Orders");
		orderService.deleteAllOrders();
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}

}
