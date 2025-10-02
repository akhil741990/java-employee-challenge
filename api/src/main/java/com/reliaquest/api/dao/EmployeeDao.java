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
import com.reliaquest.api.util.HttpRetryHandler;

import lombok.extern.slf4j.Slf4j;

import com.reliaquest.api.model.CreateEmployeeRequest;
import com.reliaquest.api.model.CreateEmployeeResponse;
import com.reliaquest.api.model.AllEmployeesResponse;

@Repository
@Slf4j
public class EmployeeDao {
	
	
	private final RestTemplate restTemplate;
	private final String serverUrl; 
	private final int maxRetries;
	private final long initialWaitInMillis;
	
	@Autowired
	public EmployeeDao(@Value("${emp.server.url}") String serverUrl,
			@Value("${emp.http.max.retries}") int maxRetries,
			@Value("${emp.http.initial.wait.in.millis}") long initialWaitInMillis,
			RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.serverUrl = serverUrl;
		this.initialWaitInMillis = initialWaitInMillis;
		this.maxRetries = maxRetries;
	}
	
	public List<Employee>getAllEmployees() {

//        ResponseEntity<AllEmployeesResponse> response = restTemplate.exchange(
//                serverUrl,
//                HttpMethod.GET,
//                HttpEntity.EMPTY,   // no headers
//                AllEmployeesResponse.class
//        );
//        return response.getBody().getData();
		log.info("fetching all employees");
        
        return HttpRetryHandler.executeWithRetry(() -> {
        	ResponseEntity<AllEmployeesResponse> response = restTemplate.exchange(
                    serverUrl,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,   
                    AllEmployeesResponse.class
            );
            return response.getBody().getData();
        }, maxRetries, initialWaitInMillis);
	}
	
	public List<Employee> getEmployeesByNameSearch(String name) {
		
		log.info("fetching employee by name search : {}", name);
		return getAllEmployees()
				.stream()
				.filter(e -> e.getName().contains(name))
				.collect(Collectors.toList());

	}
	
	public Employee getEmployeesById(String id) {
		log.info("fetching employee by id: {}", id);
		Employee emp =  getAllEmployees()
				.stream()
				.filter(e -> e.getId().equals(UUID.fromString(id)))
				.findFirst()
				.orElse(null);
		log.info("Employee with id : {}, is ; {}", id, emp);
		return emp;
	}
	
	public Integer getHighestSalaryOfEmployees() {
		log.info("fetching the highest salary");	
		Integer maxSalary =  getAllEmployees()
				.stream()
				.mapToInt(emp -> emp.getSalary())
				.max()
				.orElse(0);
		log.info("highest salary is {}", maxSalary);	
		return maxSalary;
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
		log.info("find the top 10 employees based on salary");
		List<Employee> top10EmpBasedOnSalary = new ArrayList<Employee>(minHeap);
		top10EmpBasedOnSalary
			.sort(Comparator.comparingInt(Employee::getSalary).reversed());
		return top10EmpBasedOnSalary
			.stream().map(emp -> emp.getName()).toList();
		
	}
	
	public CreateEmployeeResponse createEmployee(CreateEmployeeRequest empReq) {
		
		return HttpRetryHandler.executeWithRetry(() -> {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<CreateEmployeeRequest> request = new HttpEntity<>(empReq, headers);

	        
	        ResponseEntity<CreateEmployeeResponse> response = restTemplate.postForEntity(serverUrl, request, CreateEmployeeResponse.class);
	        return response.getBody();
		}, maxRetries, initialWaitInMillis);
		
	}
}
