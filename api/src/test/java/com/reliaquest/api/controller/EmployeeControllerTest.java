package com.reliaquest.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;
	@InjectMocks
	private EmployeeController employeeController;
	
	Employee e1;
	Employee e2;
	Employee e3;
	
	@BeforeEach
	public void init() {
		e1 = Employee.builder().id(UUID.randomUUID()).name("EMP_1").salary(1000).build();
		e2 = Employee.builder().id(UUID.randomUUID()).name("EMP_2").salary(7000).build();
		e3 = Employee.builder().id(UUID.randomUUID()).name("EMP_3").salary(5000).build();
	}
	
	@Test
	public void getAllEmployeesTest() {
		when(employeeService.getAllEmployees()).thenReturn(List.of(e1,e2,e3));
		ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, response.getBody().size());
		assertEquals("EMP_1", response.getBody().get(0).getName());
	}
	
	@Test
    void getEmployeesByNameTest() {
        when(employeeService.getEmployeesByNameSearch("_2")).thenReturn(List.of(e2));

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("_2");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("EMP_2", response.getBody().get(0).getName());
    }
	

    @Test
    void getEmployeeByIdTest() {
        when(employeeService.getEmployeesById(e1.getId().toString())).thenReturn(e1);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(e1.getId().toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("EMP_1", response.getBody().getName());
    }

    @Test
    void getEmployeeByIdNotFoundTest() {
        when(employeeService.getEmployeesById(anyString())).thenReturn(null);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(UUID.randomUUID().toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
