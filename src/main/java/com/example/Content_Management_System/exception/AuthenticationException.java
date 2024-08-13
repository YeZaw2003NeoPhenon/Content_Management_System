
package com.example.Content_Management_System.exception;

public class AuthenticationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message) {
        super(message);
    }
}