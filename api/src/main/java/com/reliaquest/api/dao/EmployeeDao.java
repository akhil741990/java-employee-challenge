package com.reliaquest.api.dao;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeResponse;

@Repository
public class EmployeeDao {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public EmployeeDao(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public List<Employee>getAllEmployees() {
		
		String url = "http://localhost:8112/api/v1/employee";  // returns JSON array of employees

        ResponseEntity<EmployeeResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,   // no headers
                EmployeeResponse.class
        );
        return response.getBody().getData();
	}
	
	public List<Employee> getEmployeesByNameSearch(String name) {
		return getAllEmployees()
				.stream()
				.filter(e -> e.getName().contains(name))
				.collect(Collectors.toList());
	}
	
	public Employee getEmployeesById(String id) {
		return getAllEmployees()
				.stream()
				.filter(e -> e.getId().equals(UUID.fromString(id)))
				.findFirst()
				.orElse(null);
	}

}
