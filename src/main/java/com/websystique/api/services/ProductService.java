package com.websystique.api.services;

import java.util.List;

import com.websystique.api.model.Product;


public interface ProductService {
	Product findById(Long id);

	Product findByName(String name);

	void createProduct(Product product);

	Product updateProduct(Product product);

	void deleteProductById(Long id);

	void deleteAllProducts();

	List<Product> findAllProducts();

	boolean isProductExist(Product product);

}
