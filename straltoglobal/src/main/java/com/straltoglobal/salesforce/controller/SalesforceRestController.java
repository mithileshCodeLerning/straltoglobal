package com.straltoglobal.salesforce.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.straltoglobal.salesforce.job.BulkProcessorServiceImpl;
import com.straltoglobal.salesforce.manager.ContactRequestMapper;
import com.straltoglobal.salesforce.service.SalesforceAPIServiceImpl;


@RestController
public class SalesforceRestController {

	@Autowired
	SalesforceAPIServiceImpl servcie;
	
	@Autowired
	 BulkProcessorServiceImpl bulkAPIService;
	
	
	@GetMapping("/contacts")
	public String getContacts()
	{
		String contacts = servcie.readSalesforceObject("contact");
		System.out.println("Response: " + contacts);
		return contacts;
	}
	
	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public String add(@RequestBody ContactRequestMapper inputContact)
	{
		ObjectMapper mapper = new ObjectMapper();
		String response =null;
		   try {
			String contactJSON = mapper.writeValueAsString(inputContact);
			response = servcie.writeSalesforceObject(contactJSON);
			System.out.println("Response: " + response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	@RequestMapping(value = "/updateContact/{id}", method = RequestMethod.POST)
	public String add(@RequestBody ContactRequestMapper inputContact, @PathVariable("id") String id)
	{
		ObjectMapper mapper = new ObjectMapper();
		   try {
			String contactJSON = mapper.writeValueAsString(inputContact);
			servcie.updateSalesforceObject(id,contactJSON);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success:true";
	}
	
	
	// bulk API
	@RequestMapping(value = "/bulkapi/job/create/{opType}", method = RequestMethod.POST)
	public String createBulkJob(@PathVariable("opType") String opType) throws JsonMappingException, JsonProcessingException
	{
		
			return bulkAPIService.createBulkJob(opType);
		
	}
	
	@RequestMapping(value = "/bulkapi/batch/status/{id}", method = RequestMethod.GET)
	public String getJobStatus( @PathVariable("id") String id)
	{
		return bulkAPIService.checkStatusOfJob(id);
	}
	
	@RequestMapping(value = "/bulkapi/batch/result/{id}", method = RequestMethod.GET)
	public String getJobResult( @PathVariable("id") String id)
	{
		return bulkAPIService.jobResults(id);
	}
}
