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
import com.websystique.api.model.User;
import com.websystique.api.services.UserService;

@Controller()
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	/**
	 * Retrieve All Users 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public ResponseEntity<?> listAllUsers() {
		logger.info("Fetching all users");
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			logger.info("No users found, returning http status code " + HttpStatus.NO_CONTENT);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/**
	 * Retrieve Single User 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.error("User with id {} not found.", id);
			throw new EntityNotFoundException(String.format("User with id %d not found.", id));
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Create a User 
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/api/users", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);
		userService.createUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update a User
	 * @param id
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	/**
	 * Delete user with id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Delete All Users 
	 * @return
	 */
	@RequestMapping(value = "api/users", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");
		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
