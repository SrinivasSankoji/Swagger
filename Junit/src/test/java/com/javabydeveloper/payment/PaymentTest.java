package com.javabydeveloper.payment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Payment")
public class PaymentTest {

	@Test
	void payment_success_test() {
		System.out.println("payment_success_test(): " + Thread.currentThread().getName());
	}

	@Test
	void payment_decline_test() {
		System.out.println("payment_decline_test(): " + Thread.currentThread().getName());
	}

	@Test
	void invalid_country_test() {
		System.out.println("invalid_country_test(): " + Thread.currentThread().getName());
	}
}
