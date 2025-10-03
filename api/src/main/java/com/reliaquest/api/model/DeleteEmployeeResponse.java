package com.reliaquest.api.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


@Jacksonized
@Builder
@Value
public class DeleteEmployeeResponse {

	private Boolean data;
	private String status;
}