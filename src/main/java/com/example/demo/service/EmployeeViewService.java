package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.EmployeeDomainRequest;
import com.example.demo.model.EmployeeViewRequest;

@Service
public class EmployeeViewService {
	
	@Autowired
	RestTemplate restTemplate;

	public ResponseEntity<String> doEmployeeDomainServiceCall(String domainServiceUrl, HttpMethod post,
			HttpEntity<EmployeeDomainRequest> entity, Class<String> employeeDomainResponse) {
		ResponseEntity<String> responseFromDomainService=restTemplate.exchange(domainServiceUrl, post, entity, employeeDomainResponse);
		return responseFromDomainService;
	}

}
