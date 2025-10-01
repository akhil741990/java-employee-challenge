package com.reliaquest.api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
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
	Integer salaray;
	@JsonProperty("employee_title")
	String title;
	@JsonProperty("employee_email")
	String email;
	
	//Eclipse lombook error in autogenerating the methods for @Data, hence adding it explicitly
	public String getName() {
		return name;
	}
	public UUID getId() {
		return id;
	}
}
