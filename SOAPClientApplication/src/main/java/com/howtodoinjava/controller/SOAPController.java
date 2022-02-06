package com.howtodoinjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.service.SOAPService;

@RestController
public class SOAPController {

	@Autowired
	private SOAPService soapService;

	@GetMapping("/soapClient/{name}")
	public Object getResponseFromSOAPService(@PathVariable("name") String name) {

		return soapService.getResponseFromSOAPService(name);
	}
}
