package com.example.demo.constroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aggreagtor.EmployeeViewServiceAggregator;
import com.example.demo.model.EmployeeViewRequest;

@RestController
public class EmployeeViewController {


	@Autowired
	EmployeeViewServiceAggregator employeeViewServiceAggregator;
	
	@PostMapping(value="/employee/employeeDetails/store",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setEmployeeDetailsRequest(@RequestBody EmployeeViewRequest employeeViewRequest){
		ResponseEntity<String> response=employeeViewServiceAggregator.setEmployeeDetails(employeeViewRequest);
		return response;
		
	}
}
