package com.howtodoinjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howtodoinjava.connector.SOAPConnector;
import com.howtodoinjava.xml.school.StudentDetailsRequest;
import com.howtodoinjava.xml.school.StudentDetailsResponse;

@Service
public class SOAPServiceImpl implements SOAPService {

	@Autowired
	private SOAPConnector soapConnector;

	public Object getResponseFromSOAPService(String name) {
		StudentDetailsRequest request = new StudentDetailsRequest();
		request.setName(name);
		StudentDetailsResponse response = (StudentDetailsResponse) soapConnector
				.callWebService("http://localhost:8080/service/student-details", request);
		System.out.println("Got Response As below ========= : ");
		System.out.println("Name : " + response.getStudent().getName());
		System.out.println("Standard : " + response.getStudent().getStandard());
		System.out.println("Address : " + response.getStudent().getAddress());
		return response;
	}

}
