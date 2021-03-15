package com.straltoglobal.salesforce.service;


import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.straltoglobal.salesforce.manager.ClientConfig;
import com.straltoglobal.salesforce.manager.ContactRequestMapper;
import com.straltoglobal.salesforce.manager.SalesforceConstant;

@Service
public class SalesforceAPIServiceImpl implements SalesforceAPIService{

	@Autowired
	ClientConfig client;
	@Override
	public String generateAccesstoken() {
		MultiValueMap<String, String> mvMap = prepareAuthCredential();
		RestTemplate restTemplate = new RestTemplate(client.clientHttpRequestFactory());
		Map<String, String> token = (Map<String, String>) restTemplate.postForObject(SalesforceConstant.oAuthURL, mvMap, Map.class);
		System.out.println("Access Token  :: " + token.get("access_token"));
		  System.out.println("Instance Url  :: " + token.get("instance_url"));
		  System.out.println("Token_Type  :: " + token.get("token_type"));
		  System.out.println("Signature  :: " + token.get("signature"));
		  return token.get("access_token");
	}

	private MultiValueMap<String, String> prepareAuthCredential() {
		MultiValueMap<String, String> mvMap = new LinkedMultiValueMap<>();
		  mvMap.add(SalesforceConstant.GRANT_TYPE, SalesforceConstant.GRANT_TYPE_VALUE);
		  mvMap.add(SalesforceConstant.CLIENT_ID, SalesforceConstant.CLIENT_TD_VALUE);
		  mvMap.add(SalesforceConstant.CLIENT_SECRET, SalesforceConstant.CLIENT_SECRET_VALUE);
		  mvMap.add(SalesforceConstant.USERNAME, SalesforceConstant.USENAME_VALUE);
		  mvMap.add(SalesforceConstant.PASSWORD, SalesforceConstant.PASSWORD_VALUE);
		  return mvMap;
	}

	@Override
	public String readSalesforceObject(String salesforceObject) {
		
		String oAuthtoken = generateAccesstoken();
		// Create Http headers and add the oauth token to them
		  HttpHeaders restHeaders = new HttpHeaders();
		  restHeaders.setContentType(MediaType.APPLICATION_JSON);
		  restHeaders.add("X-PrettyPrint", "1");
		  restHeaders.add("Authorization", "OAuth " + oAuthtoken);
		  
		  MultiValueMap<String, String> mv2Map = new LinkedMultiValueMap<>();
		  
		  // Other methods like getForEntity() or getForObject() can also be used.
		  HttpEntity<?> restRequest = new HttpEntity<>(mv2Map, restHeaders);
		  RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
		  // Make a request and read the response string
		  ResponseEntity<String> responseStr = getRestTemplate.exchange(SalesforceConstant.REST_CONTACT_URL, HttpMethod.GET, restRequest,
		    String.class);
		  return responseStr.getBody();
	}

	@Override
	public String writeSalesforceObject(String contact) {
		String oAuthtoken = generateAccesstoken();
		// Create Http headers and add the oauth token to them
		  HttpHeaders restHeaders = new HttpHeaders();
		  restHeaders.setContentType(MediaType.APPLICATION_JSON);
		  restHeaders.add("Authorization", "OAuth " + oAuthtoken);
		  
		  System.out.println(" obj:" + contact);
		  // Other methods like getForEntity() or getForObject() can also be used.
		  HttpEntity<?> restRequest = new HttpEntity<>(contact, restHeaders);
		  RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
		  return getRestTemplate.postForObject(SalesforceConstant.CREATE_CONTACT__URL, restRequest, String.class);
		  
	}

	@Override
	public void updateSalesforceObject(String id, String object) {
		String oAuthtoken = generateAccesstoken();
		  HttpHeaders restHeaders = new HttpHeaders();
		  restHeaders.setContentType(MediaType.APPLICATION_JSON);
		  restHeaders.add("Authorization", "OAuth " + oAuthtoken);
		  String url = SalesforceConstant.UDPATE_CONTACT_URL.replace("{Id}", id);
		  System.out.println(" obj:" + object);
		  // Other methods like getForEntity() or getForObject() can also be used.
		  HttpEntity<?> restRequest = new HttpEntity<>(object, restHeaders);
		  RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
		   getRestTemplate.patchForObject(url, restRequest, String.class);
	}

	@Override
	public String deleteSalesforceObject(String id) {
		  String oAuthtoken = generateAccesstoken();
		  HttpHeaders restHeaders = new HttpHeaders();
		  restHeaders.setContentType(MediaType.APPLICATION_JSON);
		  restHeaders.add("Authorization", "OAuth " + oAuthtoken);
		  String url = SalesforceConstant.UDPATE_CONTACT_URL.replace("{Id}", id);
		  System.out.println(" delete URL:" + url);
		  MultiValueMap<String, String> mv2Map = new LinkedMultiValueMap<>();
		  HttpEntity<?> restRequest = new HttpEntity<>(mv2Map, restHeaders);
		  RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
		  ResponseEntity<String> responseStr = getRestTemplate.exchange(url, HttpMethod.DELETE, restRequest,
				    String.class);
		return responseStr.getBody();
	}

}
