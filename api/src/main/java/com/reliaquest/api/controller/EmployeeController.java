package com.reliaquest.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.CreateEmployeeRequest;
import com.reliaquest.api.service.EmployeeService;

@RequestMapping("/api/v1/employee")
@RestController
public class EmployeeController implements IEmployeeController<Employee, CreateEmployeeRequest> {
	
	private final EmployeeService employeeService;
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	//@GetMapping("/getAll")
	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable("searchString") String searchString) {
		// TODO Auto-generated method stub
		List<Employee> empList = employeeService.getEmployeesByNameSearch(searchString);
		
		return empList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(empList);
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id) {
		// TODO Auto-generated method stub
		Employee emp = employeeService.getEmployeesById(id);
		return emp == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(emp);
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
	}

	@Override
	public ResponseEntity<Employee> createEmployee(CreateEmployeeRequest employeeInput) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(employeeService.createEmployee(employeeInput).getData());
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
