package com.straltoglobal.salesforce.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.MessageFormat;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class JWTOAuthSalesforce {

	/* public static void main(String[] args) {
		 generateJWTToken();
		 
	 }*/
	public static String generateJWTToken()
	{
		String jwtToken="";
		
		 String header = "{\"alg\":\"RS256\"}";
		    String claimTemplate = "'{'\"iss\": \"{0}\", \"sub\": \"{1}\", \"aud\": \"{2}\", \"exp\": \"{3}\"'}'";
		 
		    try {
		      StringBuffer token = new StringBuffer();
		 
		      //Encode the JWT Header and add it to our string to sign
		      token.append(Base64.encodeBase64URLSafeString(header.getBytes("UTF-8")));
		 
		      //Separate with a period
		      token.append(".");
		 
		      //Create the JWT Claims Object
		      String[] claimArray = new String[4];
		      claimArray[0] = "3MVG9fe4g9fhX0E5AJduYJHtgwXN9V2ulVKj5OgAmUzfEpplAroVrL3iv6Bac0ShQRLnBpFrHTBAk6tl2pRom";
		      claimArray[1] = "mithilesh.singh@straltoglobal.com";
		      claimArray[2] = "https://login.salesforce.com";
		      claimArray[3] = Long.toString( ( System.currentTimeMillis()/1000 ) + 300);
		      MessageFormat claims;
		      claims = new MessageFormat(claimTemplate);
		      String payload = claims.format(claimArray);
		 
		      //Add the encoded claims object
		      token.append(Base64.encodeBase64URLSafeString(payload.getBytes("UTF-8")));
		 
		      //Load the private key from a keystore
		      KeyStore keystore = KeyStore.getInstance("JKS");
		 
		      keystore.load(new FileInputStream("C:\\Program Files\\Java\\jre1.8.0_221\\bin\\jwt_test.jks"), "password".toCharArray());
		      PrivateKey privateKey = (PrivateKey) keystore.getKey("jwt_test", "password".toCharArray());
		      //Sign the JWT Header + "." + JWT Claims Object
		      Signature signature = Signature.getInstance("SHA256withRSA");
		      signature.initSign(privateKey);
		      signature.update(token.toString().getBytes("UTF-8"));
		      String signedPayload = Base64.encodeBase64URLSafeString(signature.sign());
		 
		      //Separate with a period
		      token.append(".");
		 
		      //Add the encoded signature
		      token.append(signedPayload);
		 
		      System.out.println(token.toString());
		      
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jwtToken; 
	}
}
