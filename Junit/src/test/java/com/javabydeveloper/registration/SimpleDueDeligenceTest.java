package com.javabydeveloper.registration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("SimpleDueDeligenceTest")
public class SimpleDueDeligenceTest {

	@Test
	void simpl_dueDeligence_pass_Test() {
		System.out.println("simpl_dueDeligence_pass_Test(): " + Thread.currentThread().getName());
	}

	@Test
	void simpl_dueDeligence_fail_Test() {
		System.out.println("simpl_dueDeligence_fail_Test(): " + Thread.currentThread().getName());
	}
}
