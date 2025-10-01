package com.reliaquest.api.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class EmployeeDao {
	
	@Autowired
	private RestTemplate restTemplate;

}
