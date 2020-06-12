package com.example.demo.model;


import lombok.Data;

@Data
public class EmployeeDomainRequest {

	private String empName;
	private String address;
	private String email;
	private String phone;
	private float experience;
	
}
