package com.straltoglobal.salesforce.job;

import org.springframework.stereotype.Component;

@Component
interface BulkProcessorService {

	String createBulkJob(String operation);
	void uploadJob(String id,String token);
	void closeJob(String jobId,String authToken);
	String checkStatusOfJob(String jobId);
	String jobResults(String jobId);
}
