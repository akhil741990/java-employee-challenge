package com.reliaquest.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class DeleteEmployeeRequest {

	@NotBlank
	private String name;
}
