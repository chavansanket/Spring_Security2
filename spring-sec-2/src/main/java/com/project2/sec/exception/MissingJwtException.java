package com.project2.sec.exception;


public class MissingJwtException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MissingJwtException(String message) {
		super(message);
	}
}
