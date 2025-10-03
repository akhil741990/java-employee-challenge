package com.reliaquest.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeDao employeeDao;
	@InjectMocks
	private EmployeeService employeeService;
	
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
	public void getEmpolyeeByIdTest() {
		when(employeeDao.getAllEmployees()).thenReturn(List.of(e1,e2));
		Employee emp = employeeService.getEmployeesById(e1.getId().toString());
		assertEquals(e1.getId(), emp.getId());
	}
	
	@Test
	public void getEmployeeHighestSalaryTest() {
		when(employeeDao.getAllEmployees()).thenReturn(List.of(e1,e2, e3));
		Integer highestSalary = employeeService.getHighestSalaryOfEmployees();
		assertEquals(e2.getSalary(), highestSalary);
	}
	
	@Test
	public void getTopTenHighestEarningEmployeeNames() {
		
	   List<Employee> allEmployees = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            allEmployees.add(Employee.builder()
                    .id(UUID.randomUUID())
                    .name("EMP_" + i)
                    .salary(i * 1000)  // salaries: 1000, 2000, ..., 15000
                    .build());
        }
        when(employeeDao.getAllEmployees()).thenReturn(allEmployees);
     
        List<String> top10Names = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(10, top10Names.size());

        // The top 10 salaries are 15000..6000
        List<String> expectedNames = allEmployees.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .limit(10)
                .map(Employee::getName)
                .toList();

        assertEquals(expectedNames, top10Names);
	}
}
