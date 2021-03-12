package com.straltoglobal.salesforce.job;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.straltoglobal.salesforce.manager.ClientConfig;
import com.straltoglobal.salesforce.manager.SalesforceConstant;

@Service
public class BulkProcessorServiceImpl implements BulkProcessorService {

	@Autowired
	ClientConfig client;
	
	@Override
	public String createBulkJob(String operation) {
		
		String authToken = generateAccesstoken();
		JSONObject createJobJson = generateCreateJobJson(operation);
		
		HttpHeaders restHeaders = new HttpHeaders();
		restHeaders.setContentType(MediaType.APPLICATION_JSON);
		restHeaders.add("Authorization", "OAuth " + authToken);
		  System.out.println("Object: " + createJobJson.toString());
		 HttpEntity<?> restRequest = new HttpEntity<>(createJobJson.toString(), restHeaders);
		 RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
		 String jobResponse = getRestTemplate.postForObject(SalesforceConstant.CREATE_JOB_URL, restRequest, String.class);
		 System.out.println(" Response1: "+ jobResponse);
		 JSONObject response  = new JSONObject(jobResponse);
		 if(response != null) {
			 String jobId = response.getString("id");
			 System.out.println("Job created. ID:: " + jobId);
			 System.out.println("Upload data");
			uploadJob(jobId,authToken);
		 }
		return jobResponse;
	}

	private JSONObject generateCreateJobJson(String operation) {
		JSONObject object = new JSONObject();
		object.put(SalesforceConstant.JSON_OPERATION_KEY, Operation.valueOf(operation));
		object.put(SalesforceConstant.JSON_OBJECT_KEY, SalesforceConstant.JSON_OBJECT_KEY_VALUE);
		object.put(SalesforceConstant.JSON_CONTENT_TYPE_KEY, SalesforceConstant.JSON_CONTENT_TYPE_VALUE);
		object.put(SalesforceConstant.JSON_LINE_ENDING_KEY, SalesforceConstant.JSON_LINE_ENDING_VALUE);
		return object;
	}

	@Override
	public void uploadJob(String jobId,String authToken) {
		if(authToken == null) {
			authToken = generateAccesstoken();
		}
		String serverUrl = SalesforceConstant.UPLOAD_JOB_URL.replace("{ID}", jobId);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(serverUrl);
		
		try {	 
		//File file = new File("C:\\Users\\mithilesh.tiwari\\Desktop\\csv\\result.csv");
        File file = ResourceUtils.getFile("classpath:"+SalesforceConstant.FILE_DIR);
        if(file.exists()) {
        	FileEntity input = new FileEntity(file);
    		input.setContentType("text/csv");
    		putRequest.setEntity(input);
    		putRequest.addHeader("Authorization", "OAuth " + authToken);
    		HttpResponse response = httpclient.execute(putRequest);
    		System.out.println("Uploaded. " + response.toString());
        }else {
        	new IOException("File not found");
        }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		// acknowledge salesforce
		closeJob(jobId,authToken);
	}

	@Override
	public void closeJob(String jobId,String authToken) {
		if(authToken != null) {
			authToken = generateAccesstoken();
		}
		HttpHeaders restHeaders = new HttpHeaders();
		restHeaders.add("Authorization", "OAuth " + authToken);
		restHeaders.add("Content-Type", "application/json; charset=UTF-8");
		String serverUrl = SalesforceConstant.CLOSE_JOB_URL.replace("{ID}", jobId);
		JSONObject object = new JSONObject();
		object.put("state", "UploadComplete");
		System.out.println("Json: "+object.toString() + "  jobId:" +serverUrl);
		HttpEntity<?> restRequest = new HttpEntity<>(object.toString(), restHeaders);
		 RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
		 String response = getRestTemplate.patchForObject(serverUrl, restRequest, String.class);
		System.out.println(" Close response: " + response);
	}

	@Override
	public String checkStatusOfJob(String jobId) {
		String response  = "";
		if(jobId != null && jobId.trim().length() > 0) {
			String token = generateAccesstoken();
			
			String serverUrl = SalesforceConstant.STATUS_JOB_URL.replace("{ID}", jobId);
			HttpHeaders restHeaders = new HttpHeaders();
			restHeaders.setContentType(MediaType.APPLICATION_JSON);
			restHeaders.add("Authorization", "OAuth " + token);
			MultiValueMap<String, String> mv2Map = new LinkedMultiValueMap<>();
			  
			  HttpEntity<?> restRequest = new HttpEntity<>(mv2Map, restHeaders);
			  RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
			  ResponseEntity<String> responseStr = getRestTemplate.exchange(serverUrl, HttpMethod.GET, restRequest,
			    String.class);
			response = responseStr.getBody();
		}
		return response;
	}
	@Override
	public String jobResults(String jobId) {
		String response  = "";
		if(jobId != null && jobId.trim().length() > 0) {
			String token = generateAccesstoken();
			String serverUrl = SalesforceConstant.STATUS_JOB_RESULT_URL.replace("{ID}", jobId);
			HttpHeaders restHeaders = new HttpHeaders();
			restHeaders.setContentType(MediaType.APPLICATION_JSON);
			restHeaders.add("Authorization", "OAuth " + token);
			MultiValueMap<String, String> mv2Map = new LinkedMultiValueMap<>();
			  
			  HttpEntity<?> restRequest = new HttpEntity<>(mv2Map, restHeaders);
			  RestTemplate getRestTemplate = new RestTemplate(client.clientHttpRequestFactory());
			  ResponseEntity<String> responseStr = getRestTemplate.exchange(serverUrl, HttpMethod.GET, restRequest,
			    String.class);
			response = responseStr.toString();
		}
		return response;
	}
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
	enum Operation{
		insert,update,delete;
	}
}
