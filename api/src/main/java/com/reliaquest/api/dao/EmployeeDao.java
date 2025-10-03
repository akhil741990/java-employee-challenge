package com.reliaquest.api.dao;


import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.model.AllEmployeesResponse;
import com.reliaquest.api.model.CreateEmployeeRequest;
import com.reliaquest.api.model.CreateEmployeeResponse;
import com.reliaquest.api.model.DeleteEmployeeRequest;
import com.reliaquest.api.model.DeleteEmployeeResponse;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.util.HttpRetryHandler;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class EmployeeDao {
	
	
	private final RestTemplate restTemplate;
	private final String serverUrl; 
	private final int maxRetries;
	private final long initialWaitInMillis;
	
	@Autowired
	public EmployeeDao(@Value("${emp.server.url}") String serverUrl,
			@Value("${emp.http.max.retries:1}") int maxRetries,
			@Value("${emp.http.initial.wait.in.millis:1000}") long initialWaitInMillis,
			RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.serverUrl = serverUrl;
		this.initialWaitInMillis = initialWaitInMillis;
		this.maxRetries = maxRetries;
	}
	
	public List<Employee>getAllEmployees() {

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
	

	
	public CreateEmployeeResponse createEmployee(CreateEmployeeRequest empReq) {
		
		return HttpRetryHandler.executeWithRetry(() -> {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<CreateEmployeeRequest> request = new HttpEntity<>(empReq, headers);

	        
	        ResponseEntity<CreateEmployeeResponse> response = restTemplate.postForEntity(serverUrl, request, CreateEmployeeResponse.class);
	        return response.getBody();
		}, maxRetries, initialWaitInMillis);
		
	}
	
	public DeleteEmployeeResponse deleteEmployee(DeleteEmployeeRequest req) {
		
		log.info("deleting employe with name : {}", req.getName());
		return HttpRetryHandler.executeWithRetry(() -> {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<DeleteEmployeeRequest> request = new HttpEntity<>(req, headers);

			ResponseEntity<DeleteEmployeeResponse> response = restTemplate.exchange(
			        serverUrl,
			        HttpMethod.DELETE,
			        request,
			        DeleteEmployeeResponse.class
			);
			return response.getBody();
		}, maxRetries, initialWaitInMillis);

		
	}
}
