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
import com.websystique.api.model.Product;
import com.websystique.api.services.ProductService;

@Controller()
public class ProductController {
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	/**
	 * Retrieve All Products 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/products", method = RequestMethod.GET)
	public ResponseEntity<?> listAllProducts() {
		logger.info("Fetching all products");
		List<Product> products = productService.findAllProducts();
		if (products.isEmpty()) {
			logger.info("No products found, returning http status code " + HttpStatus.NO_CONTENT);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	/**
	 * Retrieve Single Product 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
		logger.info("Fetching Product with id {}", id);
		Product product = productService.findById(id);
		if (product == null) {
			logger.error("Product with id {} not found.", id);
			throw new EntityNotFoundException(String.format("Product with id %d not found.", id));
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	/**
	 * Create a Product 
	 * @param product
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/api/products", method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Product : {}", product);
		productService.createProduct(product);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/product/{id}").buildAndExpand(product.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update a Product
	 * @param id
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		logger.info("Updating Product with id {}", id);
		Product updatedProduct = productService.updateProduct(product);
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
	}

	/**
	 * Delete product with id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Product with id {}", id);
		productService.deleteProductById(id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Delete All Products 
	 * @return
	 */
	@RequestMapping(value = "api/products", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteAllProducts() {
		logger.info("Deleting All Products");
		productService.deleteAllProducts();
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

}
