package com.softwaretestinghelp;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestCaseTwo {

	String name1 = "Bhaumik";

	/**
	 * Test Case using JUnit4 
	 */
	@Test
	public void compareString() {
		String name2 = "Bhaumik";
		Assertions.assertEquals(name1, name2);
	}
}
