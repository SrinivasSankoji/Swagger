package com.isuite.rjil.iagent.jiomoney.common;

public class NomineeDetails {
private String name;
private String relationship;
private String age;
String bankId;
String guardianFName;
String guardianLName;
String guardianAge;
String guardianAdd;
private String dob;
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getGuardianFName() {
	return guardianFName;
}
public void setGuardianFName(String guardianFName) {
	this.guardianFName = guardianFName;
}
public String getGuardianLName() {
	return guardianLName;
}
public void setGuardianLName(String guardianLName) {
	this.guardianLName = guardianLName;
}
public String getGuardianAge() {
	return guardianAge;
}
public void setGuardianAge(String guardianAge) {
	this.guardianAge = guardianAge;
}
public String getGuardianAdd() {
	return guardianAdd;
}
public void setGuardianAdd(String guardianAdd) {
	this.guardianAdd = guardianAdd;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getRelationship() {
	return relationship;
}
public void setRelationship(String relationship) {
	this.relationship = relationship;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
}
