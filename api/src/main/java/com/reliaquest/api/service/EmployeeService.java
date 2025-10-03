package com.reliaquest.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.model.CreateEmployeeRequest;
import com.reliaquest.api.model.CreateEmployeeResponse;
import com.reliaquest.api.model.DeleteEmployeeRequest;
import com.reliaquest.api.model.DeleteEmployeeResponse;
import com.reliaquest.api.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

	private final EmployeeDao employeeDao;
	@Autowired
	public EmployeeService(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}
	
	public List<Employee> getEmployeesByNameSearch(String name){
		log.info("fetching employee by name search : {}", name);
		return employeeDao.getAllEmployees()
				.stream()
				.filter(e -> e.getName().contains(name))
				.collect(Collectors.toList());
	}
	
	public Employee getEmployeesById(String id){
		log.info("fetching employee by id: {}", id);
		Employee emp =  employeeDao.getAllEmployees()
				.stream()
				.filter(e -> e.getId().equals(UUID.fromString(id)))
				.findFirst()
				.orElse(null);
		log.info("Employee with id : {}, is ; {}", id, emp);
		return emp;
	}
	
	public List<String> getTopTenHighestEarningEmployeeNames() {
		List<Employee> employees = employeeDao.getAllEmployees();
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
	
	public Integer getHighestSalaryOfEmployees() {
		log.info("fetching the highest salary");	
		Integer maxSalary =  employeeDao.getAllEmployees()
				.stream()
				.mapToInt(emp -> emp.getSalary())
				.max()
				.orElse(0);
		log.info("highest salary is {}", maxSalary);	
		return maxSalary;
	}
	
	public CreateEmployeeResponse createEmployee(CreateEmployeeRequest empReq) {
		return employeeDao.createEmployee(empReq);
	}
	
	public boolean deleteEmployeeById(String id) {
		Employee emp = getEmployeesById(id);
		if(emp == null) {
			return false;
		}
		DeleteEmployeeRequest req = DeleteEmployeeRequest.builder().name(emp.getName()).build();
		
		return  employeeDao.deleteEmployee(req);
	}
}
