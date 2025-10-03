package com.reliaquest.api.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.reliaquest.api.model.AllEmployeesResponse;
import com.reliaquest.api.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoTest {

	@Mock
	private RestTemplate restTemplate;
	
	private EmployeeDao employeeDao;
	
	
	
	@BeforeEach
	void init() {
		employeeDao = new EmployeeDao("", 1, 0, restTemplate);
	}
	
	@Test
	void getAllEmployeesTest() {
			Employee e1 = Employee.builder().name("EMP_1").salary(1000).build();
			Employee e2 = Employee.builder().name("EMP_2").salary(2000).build();
			AllEmployeesResponse mockResponse = AllEmployeesResponse.builder().data(List.of(e1,e2)).build();
			
			ResponseEntity<AllEmployeesResponse> responseEntity =
	                new ResponseEntity<>(mockResponse, HttpStatus.OK);
			
			
			when(restTemplate.exchange(
	                anyString(),
	                any(HttpMethod.class),
	                any(HttpEntity.class),
	                ArgumentMatchers.<Class<AllEmployeesResponse>>any()
	        )).thenReturn(responseEntity);
			
			List<Employee> response = employeeDao.getAllEmployees();
			
				
			assertEquals(2,response.size());
			assertEquals(response.get(0).getName(), "EMP_1");
			assertEquals(response.get(0).getSalary(), 1000);
			
	}
}
