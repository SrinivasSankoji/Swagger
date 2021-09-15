package com.swagger.techprimers.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swagger.techprimers.model.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {

	@ApiOperation(value = "To Get all the Users")
	@GetMapping
	public List<User> getUsers() {

		return Arrays.asList(new User("Sam", 2000L), new User("Peter", 1000L));
	}

	@ApiOperation(value = "Get the UserDetails by UserName")
	@GetMapping("/{userName}")
	public User getUser(@ApiParam(name="userName",required = true,value = "Accepts the UserName")  @PathVariable("userName") final String userName) {
		return new User(userName, 1000L);
	}
	
	@PostMapping("/createUser")
	public User createUser(@ApiParam("${createUser}") @RequestBody User user)
	{
		return new User(user.getUserName(), user.getSalary());
	}
}
