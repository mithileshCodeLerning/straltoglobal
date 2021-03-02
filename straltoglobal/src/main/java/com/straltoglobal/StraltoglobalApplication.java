package com.straltoglobal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
"com.straltoglobal.salesforce"})
public class StraltoglobalApplication {

	public static void main(String[] args) {
		SpringApplication.run(StraltoglobalApplication.class, args);
	}

}
