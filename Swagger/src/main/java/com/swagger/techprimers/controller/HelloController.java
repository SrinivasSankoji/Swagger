package com.swagger.techprimers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/hello")
@Api(value = "Hello World Controller")
public class HelloController {

	@ApiOperation(value = "Basic Hello World")
	@GetMapping
    public String hello() {
        return "Hello World";
    }

	@ApiOperation(value = "Submit User Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200,message = "Success")
	})
    @PostMapping("/post")
    public String helloPost(@RequestBody final String hello) {
        return hello;
    }

	@ApiOperation(value = "Update User Data")
    @PutMapping("/put")
    public String helloPut(@RequestBody final String hello) {
        return hello;
    }
}
