package com.prs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropertRentalSystemApplication {

	public static void main(String[] args) {
//		 Dotenv dotenv = Dotenv.load(); // Load .env variables
//	        System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
//	        System.setProperty("DATASOURCE_USER", dotenv.get("DATASOURCE_USER"));
//	        System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));
//	        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
//	        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
//	        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
//	        System.setProperty("STRIPE_SECRET_KEY", dotenv.get("STRIPE_SECRET_KEY"));
//	        System.setProperty("FRONTEND_URL", dotenv.get("FRONTEND_URL"));
		SpringApplication.run(PropertRentalSystemApplication.class, args);
	}

}
