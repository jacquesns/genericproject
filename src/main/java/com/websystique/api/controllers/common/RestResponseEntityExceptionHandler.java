package com.websystique.api.controllers.common;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
	
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    	ex.printStackTrace();
    	ResponseBody body = new ResponseBody(ex.getMessage());
        return handleExceptionInternal(ex, body, 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {
    	ResponseBody body = new ResponseBody(ex.getMessage());
        return handleExceptionInternal(ex, body, 
          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {EntityWithKeyAlreadyExistsException.class, IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleEntityExistsException(RuntimeException ex, WebRequest request) {
    	ResponseBody body = new ResponseBody(ex.getMessage());
        return handleExceptionInternal(ex, body, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, TransactionSystemException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(RuntimeException ex, WebRequest request) {
    	Throwable e = ex;
    	if(ex instanceof TransactionSystemException){
    		e = ((TransactionSystemException) ex).getRootCause();
    	}
    	ResponseBody body = new ResponseBody(e.getMessage());
        return handleExceptionInternal(ex, body, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    

	private class ResponseBody {
		private String message;

		public ResponseBody(String message) {
			super();
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
		
	}
    
}