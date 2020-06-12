package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeViewRequest {

	private String empName;
	private String addess;
	private String dateOfJoining;
	private String phone;
	private String techStack;

}
