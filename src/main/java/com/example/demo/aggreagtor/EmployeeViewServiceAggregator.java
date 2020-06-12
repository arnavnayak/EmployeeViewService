package com.example.demo.aggreagtor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.model.EmployeeDomainRequest;
import com.example.demo.model.EmployeeViewRequest;
import com.example.demo.service.EmployeeViewService;

@Component
public class EmployeeViewServiceAggregator {

	@Autowired
	EmployeeViewService employeeViewService;
	
	
	@Autowired
	LoadBalancerClient ribbonLoadBalancerClient;
	//TODO to find out the service name and take those values from config files
	String domainServiceName="http://localhost:9091/";
	String domainServiceEndpoint="/employee/domain/details/save";
	
	public ResponseEntity<String> setEmployeeDetails(EmployeeViewRequest employeeViewrequest){
		EmployeeDomainRequest employeeDomainRequest = creatingDomainServiceRequest(employeeViewrequest);
		String domainServiceUrl=createDomainServiceUrl(domainServiceName,domainServiceEndpoint);
		HttpEntity<EmployeeDomainRequest> entity=new HttpEntity<>(employeeDomainRequest); 
		
		return employeeViewService.doEmployeeDomainServiceCall(domainServiceUrl,HttpMethod.POST,entity,String.class);
		
	}

	private EmployeeDomainRequest creatingDomainServiceRequest(EmployeeViewRequest employeeViewrequest) {
		EmployeeDomainRequest employeeDomainRequest=new EmployeeDomainRequest();
		employeeDomainRequest.setEmpName(employeeViewrequest.getEmpName());
		employeeDomainRequest.setAddress(employeeViewrequest.getAddess());
		employeeDomainRequest.setPhone(employeeViewrequest.getPhone());
		//TODO need to compare with fetching from database the email should not be duplicated
		employeeDomainRequest.setEmail(employeeViewrequest.getEmpName().concat("@xyz.com"));
		String doj=employeeViewrequest.getDateOfJoining();
		float experience = totalExperience(doj);
		employeeDomainRequest.setExperience(experience);
		return employeeDomainRequest;
	
	}

	private float totalExperience(String doj) {
		LocalDate today = LocalDate.now();
		LocalDate d1 = LocalDate.parse(doj, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate d2 = LocalDate.parse(today.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
		Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		long diffDays = diff.toDays();
		float experience = diffDays/360;
		return experience;
	}

	private String createDomainServiceUrl(String serviceName,String serviceEndpoint) {
		String url="";
		ServiceInstance instance=this.ribbonLoadBalancerClient.choose(serviceName);
		if(null!=instance) {
			url=instance.getUri().toString().concat(serviceEndpoint);
		}
		return url;
	}
	
}
