package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class EmployeeRequest {
	String name;
	Integer age;
	Integer salary;
	String title;
}
