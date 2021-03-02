package com.straltoglobal.salesforce.manager;

public final class SalesforceConstant {
	public static final String oAuthURL="https://login.salesforce.com/services/oauth2/token";
	public static final String REST_CONTACT_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/query/?q=SELECT+Name+FROM+Contact";
	public static final String REST_CONTACT_POST_URL="https://start3-dev-ed.my.salesforce.com/services/data/v51.0/sobjects/Contact/";
	
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
