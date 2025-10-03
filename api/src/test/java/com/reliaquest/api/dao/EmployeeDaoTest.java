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
import com.reliaquest.api.model.CreateEmployeeRequest;
import com.reliaquest.api.model.CreateEmployeeResponse;
import com.reliaquest.api.model.DeleteEmployeeRequest;
import com.reliaquest.api.model.DeleteEmployeeResponse;
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
			
//			when(restTemplate.exchange(
//	                anyString(),
//	                HttpMethod.GET,
//	                HttpEntity.EMPTY,
//	                ArgumentMatchers.<Class<AllEmployeesResponse>>any()
//	        )).thenReturn(responseEntity);
			
			List<Employee> response = employeeDao.getAllEmployees();
			
				
			assertEquals(2,response.size());
			assertEquals(response.get(0).getName(), "EMP_1");
			assertEquals(response.get(0).getSalary(), 1000);
			
	}
	
	
	@Test
	void createEmployeeTest() {
			Employee e1 = Employee.builder()
					.name("EMP_1")
					.salary(1000)
					.title("EMP_title")
					.age(27)
					.build();
			CreateEmployeeRequest req = CreateEmployeeRequest.builder()
											.name(e1.getName())
											.salary(e1.getSalary())
											.age(e1.getAge())
											.title(e1.getTitle())
											.build();
			
			CreateEmployeeResponse mockResponse = CreateEmployeeResponse.builder()
					.data(e1)
					.status("Successfully processed request")
					.build();
			
			ResponseEntity<CreateEmployeeResponse> responseEntity =
	                new ResponseEntity<>(mockResponse, HttpStatus.OK);
			
			
			when(restTemplate.postForEntity(
	                anyString(),
	                any(HttpEntity.class),
	                ArgumentMatchers.<Class<CreateEmployeeResponse>>any()
	        )).thenReturn(responseEntity);
			

			
			CreateEmployeeResponse response = employeeDao.createEmployee(req);
			
			
			assertEquals("EMP_1", response.getData().getName());
			assertEquals(1000, response.getData().getSalary());
			assertEquals("EMP_title",response.getData().getTitle());
			assertEquals(27, response.getData().getAge());
			
	}
	
	@Test
	void deleteEmployeeTest() {
	
			DeleteEmployeeRequest req = DeleteEmployeeRequest.builder().name("EMP_1").build();
			
			DeleteEmployeeResponse mockResponse = DeleteEmployeeResponse
					.builder()
					.data(true)
					.status("Successfully processed request.")
					.build();
			
			ResponseEntity<DeleteEmployeeResponse> responseEntity =
	                new ResponseEntity<>(mockResponse, HttpStatus.OK);
			
			
			when(restTemplate.exchange(
					anyString(),
	                any(HttpMethod.class),
	                any(HttpEntity.class),
	                ArgumentMatchers.<Class<DeleteEmployeeResponse>>any()
	        )).thenReturn(responseEntity);
			

			
			DeleteEmployeeResponse response = employeeDao.deleteEmployee(req);
			
			
			assertEquals(true, response.getData());
			assertEquals("Successfully processed request.", response.getStatus());
		
			
	}
}
