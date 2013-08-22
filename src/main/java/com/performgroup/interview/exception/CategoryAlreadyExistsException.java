package com.performgroup.interview.exception;

public class CategoryAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -3288715743761071404L;

	public CategoryAlreadyExistsException() {
		super();
	}

	public CategoryAlreadyExistsException(String message) {
		super(message);
	}
}
