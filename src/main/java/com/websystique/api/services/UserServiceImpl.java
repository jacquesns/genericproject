package com.websystique.api.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.api.controllers.common.EntityNotFoundException;
import com.websystique.api.controllers.common.EntityWithKeyAlreadyExistsException;
import com.websystique.api.model.User;
import com.websystique.api.repositories.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
	@Override
	public void createUser(User user) {
		if (isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			throw new EntityWithKeyAlreadyExistsException("Unable to create. A User with name " + 
			user.getName() + " already exist.");
		}		
		userRepository.save(user);
	}

	@Override
	public User updateUser(User user){
		User currentUser = findById(user.getId());		
		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", user.getId());
			throw new EntityNotFoundException("Unable to upate. User with id " + user.getId() + " not found.");
		}
		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());
		
		return userRepository.save(user);
	}
	
	@Override
	public void deleteUserById(Long id){
		User user = findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			throw new EntityNotFoundException("Unable to delete. User with id " + id + " not found.");
		}
		userRepository.delete(id);
	}

	@Override
	public void deleteAllUsers(){
		userRepository.deleteAll();
	}

	@Override
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	@Override
	public boolean isUserExist(User user) {
		return findByName(user.getName()) != null;
	}

}