package com.straltoglobal.salesforce.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.straltoglobal.salesforce.service.SalesforceAPIServiceImpl;


@RestController
public class SalesforceRestController {

	@Autowired
	SalesforceAPIServiceImpl servcie;
	
	@GetMapping("/contacts")
	public String getContacts()
	{
		String contacts = servcie.readSalesforceObject("contact");
		System.out.println("Response: " + contacts);
		return contacts;
	}
	
	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public String add(@RequestBody JSONObject inputJson)
	{
		String contacts = servcie.writeSalesforceObject(inputJson);
		System.out.println("Response: " + contacts);
		return contacts;
	}
}
