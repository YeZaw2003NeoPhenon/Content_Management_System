package com.example.Content_Management_System.exception;

public class AuthorizationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String message) {
        super(message);
    }
}