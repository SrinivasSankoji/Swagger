package com.softwaretestinghelp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

	@Test
	public void test() {
		assertEquals("Hello world", "Hello world");
	}

	@Test
	@Disabled
	public void testHelloWorld() {
		HelloWorld helloWorld = new HelloWorld("Hello world");
		assertEquals(helloWorld.getMessage(), "Hello world");
	}
}
