package com.straltoglobal.salesforce.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
interface SalesforceAPIService {
public String generateAccesstoken();
public String readSalesforceObject(String Object);
public String writeSalesforceObject(JSONObject object);
}
