package com.reliaquest.api.exception;



import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiErrorCode {

	BAD_REQUEST("API_EMP_1000_400", "Bad Request"), // Bad Request Error Code range 1000 -> 1999 
	TOO_MANY_REQUESTS("API_EMP_2000_429", "Too many Requests");
	private final String code;
	private final String message;
	
	public int getHttpCode() {
		String httpCode = code.substring(13); // fecthing the HTTP from the error code
		return Integer.parseInt(httpCode);
	}
}
