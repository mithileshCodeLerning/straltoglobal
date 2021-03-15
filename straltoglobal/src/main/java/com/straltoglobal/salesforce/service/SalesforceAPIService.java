package com.straltoglobal.salesforce.service;

import org.springframework.stereotype.Service;

import com.straltoglobal.salesforce.manager.ContactRequestMapper;

@Service
interface SalesforceAPIService {
public String generateAccesstoken();
public String readSalesforceObject(String Object);
public String writeSalesforceObject(String object);
public String deleteSalesforceObject(String id);
public void updateSalesforceObject(String id,String object);
}
