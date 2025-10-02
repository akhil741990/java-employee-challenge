package com.reliaquest.api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class Employee {

	@JsonProperty("id")
	UUID id;
	@JsonProperty("employee_name")
	String name;
	@JsonProperty("employee_age")
	Integer age;
	@JsonProperty("employee_salary")
	Integer salary;
	@JsonProperty("employee_title")
	String title;
	@JsonProperty("employee_email")
	String email;
	
}
