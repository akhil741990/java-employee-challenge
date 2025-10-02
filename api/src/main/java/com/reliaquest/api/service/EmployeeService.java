package com.reliaquest.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliaquest.api.dao.EmployeeDao;
import com.reliaquest.api.model.Employee;

@Service
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
		return employeeDao.getEmployeesByNameSearch(name);
	}
	
	public Employee getEmployeesById(String id){
		return employeeDao.getEmployeesById(id);
	}
	
	public List<String> getTopTenHighestEarningEmployeeNames() {
		return employeeDao.getTopTenHighestEarningEmployeeNames();
	}
	
	public Integer getHighestSalaryOfEmployees() {
		return employeeDao.getHighestSalaryOfEmployees();
	}
}
