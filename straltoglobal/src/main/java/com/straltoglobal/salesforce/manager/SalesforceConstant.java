package com.straltoglobal.salesforce.manager;

public final class SalesforceConstant {
	public static final String oAuthURL="https://login.salesforce.com/services/oauth2/token";
	public static final String REST_CONTACT_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/query/?q=SELECT+id,lastname,firstname,name,Salutation+FROM+Contact";
	public static final String CREATE_CONTACT__URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/sobjects/Contact/";
	public static final String UDPATE_CONTACT_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/sobjects/Contact/{Id}";
	public static final String DELETE_CONTACT_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/sobjects/Contact/{Id}";
	
	// BULK JOB 
	public static final String CREATE_JOB_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/jobs/ingest";
	public static final String UPLOAD_JOB_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/jobs/ingest/{ID}/batches";
	public static final String CLOSE_JOB_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/jobs/ingest/{ID}";
	public static final String STATUS_JOB_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/jobs/ingest/{ID}";
	public static final String STATUS_JOB_RESULT_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/jobs/ingest/{ID}/successfulResults";
	
	//CREATE JOB CONSTANT
	public static final String JSON_OPERATION_KEY="operation";
	public static final String JSON_OPERATION_KEY_VALUE="insert";
	public static final String JSON_OBJECT_KEY="object";
	public static final String JSON_OBJECT_KEY_VALUE="Contact";
	public static final String JSON_CONTENT_TYPE_KEY="contentType";
	public static final String JSON_CONTENT_TYPE_VALUE="CSV";
	public static final String JSON_LINE_ENDING_KEY="lineEnding";
	public static final String JSON_LINE_ENDING_VALUE="CRLF";
	
	public static final String FILE_DIR="csv/result.csv";
	
	
	public static final String GRANT_TYPE="grant_type";
	public static final String GRANT_TYPE_VALUE="password";
	
	public static final String CLIENT_ID="client_id";
	public static final String CLIENT_TD_VALUE="3MVG9fe4g9fhX0E5AJduYJHtgwdCqcsa2zNt9y3yK5yPPdM56VKE_o5INF0Ms_s7aQeIy_W49_iSU_Ruuf.6I";
	public static final String CLIENT_SECRET="client_secret";
	public static final String CLIENT_SECRET_VALUE="2C6EC8B9164AF53D7F51032FCA0FC5D2B21D63ACA9DF3284BE10191C8F23ED35";
	
	public static final String USERNAME="username";
	public static final String USENAME_VALUE="mithilesh.singh@straltoglobal.com";
	public static final String PASSWORD="password";
	public static final String PASSWORD_VALUE="System12YdQSsEUIbgKioP5Dag1cMXWi";
	
	public static final Integer TIMEOUT = 6000;
}
