package com.iw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EPaymentServiceApplication.class, args);
	}

}
