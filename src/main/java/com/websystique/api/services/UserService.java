package com.websystique.api.services;

import java.util.List;

import com.websystique.api.model.User;


public interface UserService {
	User findById(Long id);

	User findByName(String name);

	void createUser(User user);

	User updateUser(User user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserExist(User user);

}
