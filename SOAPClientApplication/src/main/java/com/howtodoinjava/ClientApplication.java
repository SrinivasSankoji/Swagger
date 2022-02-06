package com.howtodoinjava;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.howtodoinjava.configuration.SOAPClientConfiguration;
import com.howtodoinjava.connector.SOAPConnector;
import com.howtodoinjava.xml.school.StudentDetailsRequest;
import com.howtodoinjava.xml.school.StudentDetailsResponse;

public class ClientApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				SOAPClientConfiguration.class);
		SOAPConnector soapConnector = context.getBean(SOAPConnector.class);
		StudentDetailsRequest request = new StudentDetailsRequest();
		request.setName("Sajal");
		StudentDetailsResponse response = (StudentDetailsResponse) soapConnector
				.callWebService("http://localhost:8080/service/student-details", request);
		System.out.println("Got Response As below ========= : ");
		System.out.println("Name : " + response.getStudent().getName());
		System.out.println("Standard : " + response.getStudent().getStandard());
		System.out.println("Address : " + response.getStudent().getAddress());
		context.close();
	}
}
