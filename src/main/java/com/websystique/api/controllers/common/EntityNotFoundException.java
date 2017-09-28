package com.websystique.api.controllers.common;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 6606334164209934044L;

}
