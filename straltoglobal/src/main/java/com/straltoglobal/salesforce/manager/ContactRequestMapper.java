package com.straltoglobal.salesforce.manager;

import org.springframework.stereotype.Component;

@Component
public class ContactRequestMapper {

	private String lastname;
	private String firstname;
	private String salutation;
	
	public String getLastname() {
		return lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getSalutation() {
		return salutation;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
}
