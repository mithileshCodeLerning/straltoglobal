package com.straltoglobal.salesforce.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientConfig {
	@Bean
	 public ClientHttpRequestFactory clientHttpRequestFactory() {
	  HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	  clientHttpRequestFactory.setConnectTimeout(SalesforceConstant.TIMEOUT);
	  clientHttpRequestFactory.setConnectionRequestTimeout(SalesforceConstant.TIMEOUT);
	  clientHttpRequestFactory.setReadTimeout(SalesforceConstant.TIMEOUT);
	  return clientHttpRequestFactory;
	 }
}
