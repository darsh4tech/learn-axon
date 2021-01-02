package com.iw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.iw.demo.domain.AxonConfig;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
//@Import(AxonConfig.class) 
public class EOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EOrderServiceApplication.class, args);
	}

}
