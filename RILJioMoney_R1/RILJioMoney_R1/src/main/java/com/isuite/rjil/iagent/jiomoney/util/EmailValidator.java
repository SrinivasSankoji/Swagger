package com.isuite.rjil.iagent.jiomoney.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	// This Changes Done By Abdul
	// private Pattern pattern;
	// private Matcher matcher;
	// /*private static final String EMAIL_PATTERN =
	// "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	// + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";*/
	// private static final String EMAIL_PATTERN = "^[ A-Za-z0-9@._]*$";
	//
	// public EmailValidator() {
	// pattern = Pattern.compile(EMAIL_PATTERN);
	// }
	//
	// public boolean validate(final String email) {
	// boolean flag=false;
	// matcher = pattern.matcher(email);
	// flag= matcher.matches();
	// return flag;
	//
	//
	// }

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean validate(final String email) {

		matcher = pattern.matcher(email);
		return matcher.matches();

	}

}
