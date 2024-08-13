package com.example.Content_Management_System.exception;

public class FileUploadException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadException(String message) {
        super(message);
    }
}