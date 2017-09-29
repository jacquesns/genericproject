package com.websystique.api.controllers.common;

public class EntityWithKeyAlreadyExistsException extends RuntimeException {

	public EntityWithKeyAlreadyExistsException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 6606334164209934044L;

}
