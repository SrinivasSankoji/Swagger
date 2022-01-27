package com.javabydeveloper.registration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Registration")
public class RegistrationTest {

	@Tag("Registration Success")
	@Test
	void registration_Success_Test() {
		System.out.println("registration_Success_Test(): " + Thread.currentThread().getName());
	}

	@Tag("Registration Failure")
	@Test
	void registration_Fail_On_DocCheck_Test() {
		System.out.println("registration_Fail_On_DocCheck_Test(): " + Thread.currentThread().getName());
	}
}
