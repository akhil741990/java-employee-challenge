package com.reliaquest.api.dao;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.CreateEmployeeRequest;
import com.reliaquest.api.model.CreateEmployeeResponse;
import com.reliaquest.api.model.AllEmployeesResponse;

@Repository
public class EmployeeDao {
	
	
	private RestTemplate restTemplate;
	private String serverUrl; 
	
	@Autowired
	public EmployeeDao(@Value("${emp.server.url}") String serverUrl, RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.serverUrl = serverUrl;
	}
	
	public List<Employee>getAllEmployees() {

        ResponseEntity<AllEmployeesResponse> response = restTemplate.exchange(
                serverUrl,
                HttpMethod.GET,
                HttpEntity.EMPTY,   // no headers
                AllEmployeesResponse.class
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
	
	public Integer getHighestSalaryOfEmployees() {
		return getAllEmployees()
				.stream()
				.mapToInt(emp -> emp.getSalary())
				.max()
				.orElse(0);
	}

	public List<String> getTopTenHighestEarningEmployeeNames() {
		List<Employee> employees = getAllEmployees();
		PriorityQueue<Employee> minHeap = new PriorityQueue<Employee>(Comparator.comparingInt(Employee::getSalary));
		employees.forEach(emp -> {
			minHeap.offer(emp);
			if(minHeap.size() > 10) {
				minHeap.poll();
			}
		});
		List<Employee> top10EmpBasedOnSalary = new ArrayList<Employee>(minHeap);
		top10EmpBasedOnSalary
			.sort(Comparator.comparingInt(Employee::getSalary).reversed());
		return top10EmpBasedOnSalary
			.stream().map(emp -> emp.getName()).toList();
		
	}
	
	public CreateEmployeeResponse createEmployee(CreateEmployeeRequest empReq) {
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateEmployeeRequest> request = new HttpEntity<>(empReq, headers);

        
        ResponseEntity<CreateEmployeeResponse> response = restTemplate.postForEntity(serverUrl, request, CreateEmployeeResponse.class);
        return response.getBody();
	}
}
