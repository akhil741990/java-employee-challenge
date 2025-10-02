package com.reliaquest.api.exception;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiException extends RuntimeException {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApiErrorCode apiErrorCode;
	private Exception exceptionCause;
	private String message;

}
