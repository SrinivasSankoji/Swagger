package com.softwaretestinghelp;

import lombok.Data;

@Data
public class HelloWorld {

	private String message;

	public HelloWorld(String message) {
		super();
		this.message = message;
	}
	
}
