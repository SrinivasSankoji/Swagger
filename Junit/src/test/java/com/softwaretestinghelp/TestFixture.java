package com.softwaretestinghelp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFixture {

	@BeforeAll
	public static void preClass() {
		System.out.println("This is the preClass() that executes only Once Before All the Test Cases");
	}

	@BeforeEach
	public void setUp() {
		System.out.println("This is the setUp() that executes Before every Test case");
	}

	@Test
	public void test1() {
		System.out.println("This is the testcase test1() in this class");
	}

	@Test
	public void test2() {
		System.out.println("This is the testcase test2() in this class");
	}

	@Test
	public void test3() {
		System.out.println("This is the testcase test3() in this class");
	}

	@AfterEach
	public void tearDown() {
		System.out.println("This is the tearDown() that executes After every Test case");
		System.out.println("\n");
	}
	
	@AfterAll
	public static void postClass() {
		System.out.println("This is the postClass() that executes only Once After All the Test Cases");
	}
}
