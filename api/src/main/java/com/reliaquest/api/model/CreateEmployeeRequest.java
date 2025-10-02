package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class CreateEmployeeRequest {
	@NotBlank(message ="name must not be blank")
	String name;
	@Min(value = 16, message = "Age must be at least 16")
    @Max(value = 65, message = "Age must be at most 65")
	Integer age;
	@NotNull(message = "Salary must not be null")
    @Positive(message = "Salary must be greater than 0")
	Integer salary;
	@NotBlank(message ="title must not be blank")
	String title;
}

