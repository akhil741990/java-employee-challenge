package com.reliaquest.api.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {
	private String code;
	private String message;
}
