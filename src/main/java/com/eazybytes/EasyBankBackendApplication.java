package com.eazybytes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity	//completely optional inside the Spring Boot environment, but mandatory for Spring environment.
//@EnableJpaRepositories("com.eazybytes.repository")	//not needed since this class is in the top level
//@EntityScan("com.eazybytes.model")		//not needed since this class is in the top level
public class EasyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBankBackendApplication.class, args);
	}

}
