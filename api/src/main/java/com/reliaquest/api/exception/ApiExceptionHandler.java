package com.reliaquest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ApiError> handleApiException(ApiException exception){
		
		HttpStatus httpStatus = HttpStatus.valueOf(exception.getApiErrorCode().getHttpCode());
		ApiError apiError = buildApiError(exception.getApiErrorCode().getCode(), exception.getMessage());
		return new ResponseEntity<>(apiError, httpStatus);
	}
	
	private ApiError buildApiError(String code, String mesage) {
		return ApiError.builder()
				.code(code)
				.message(mesage)
				.build();
	}

}