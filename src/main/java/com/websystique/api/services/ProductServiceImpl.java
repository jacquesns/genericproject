package com.websystique.api.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.api.controllers.common.EntityNotFoundException;
import com.websystique.api.controllers.common.EntityWithKeyAlreadyExistsException;
import com.websystique.api.model.Product;
import com.websystique.api.repositories.ProductRepository;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService{
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product findById(Long id) {
		return productRepository.findOne(id);
	}

	@Override
	public Product findByName(String name) {
		return productRepository.findByName(name);
	}
	
	@Override
	public void createProduct(Product product) {
		if (isProductExist(product)) {
			logger.error("Unable to create. A Product with name {} already exist", product.getName());
			throw new EntityWithKeyAlreadyExistsException("Unable to create. A Product with name " + 
			product.getName() + " already exist.");
		}		
		productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product){
		Product currentProduct = findById(product.getId());		
		if (currentProduct == null) {
			logger.error("Unable to update. Product with id {} not found.", product.getId());
			throw new EntityNotFoundException("Unable to upate. Product with id " + product.getId() + " not found.");
		}
		currentProduct.setName(product.getName());
		currentProduct.setDescription(product.getDescription());
		currentProduct.setPrice(product.getPrice());
		
		return productRepository.save(product);
	}
	
	@Override
	public void deleteProductById(Long id){
		Product product = findById(id);
		if (product == null) {
			logger.error("Unable to delete. Product with id {} not found.", id);
			throw new EntityNotFoundException("Unable to delete. Product with id " + id + " not found.");
		}
		productRepository.delete(id);
	}

	@Override
	public void deleteAllProducts(){
		productRepository.deleteAll();
	}

	@Override
	public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	@Override
	public boolean isProductExist(Product product) {
		return findByName(product.getName()) != null;
	}

}