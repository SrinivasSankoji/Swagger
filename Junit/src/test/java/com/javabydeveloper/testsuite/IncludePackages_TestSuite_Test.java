package com.javabydeveloper.testsuite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Run Junit5 Test Cases in Junit4")
@SelectPackages("com.javabydeveloper.payment")
//@SelectClasses(PaymentTest.class)
public class IncludePackages_TestSuite_Test {
}
