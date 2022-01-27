package com.javabydeveloper.payment;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

@Tag("Surcharge")
public class SurchargeTest {

	@Test
	public void surcharge_merchant_test() {
		System.out.println("surcharge_merchant_test(): " + Thread.currentThread().getName());
	}

	@Test
	public void surcharge_byCountry_test() {
		System.out.println("surcharge_byCountry_test(): " + Thread.currentThread().getName());
	}

	@Test
	public void surcharge_formula_test() {
		System.out.println("surcharge_formula_test(): " + Thread.currentThread().getName());
	}
}
