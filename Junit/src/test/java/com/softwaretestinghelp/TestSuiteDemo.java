package com.softwaretestinghelp;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCaseOne.class, TestCaseTwo.class })
public class TestSuiteDemo {

	/**
	 * Test Case using JUnit4 
	 */
	@Before
	public static void runJunitTestSuite() {
		System.out.println("Running Junit Test Suite for Test Cases TestCaseOne and TestCaseTwo");
	}
}
