//package com.iw.demo;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//
//import org.axonframework.commandhandling.CommandBus;
//import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
//import org.axonframework.commandhandling.gateway.RetryScheduler;
//import org.axonframework.config.Configurer;
//import org.axonframework.config.DefaultConfigurer;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OrderConfig {
//	
//	@Bean
//	@Qualifier("defaultCommandGateway")
//	public CommandGateway defaultCommandGateway() {
//		
//		Configurer configurer = DefaultConfigurer.defaultConfiguration();
//		CommandBus commandBus = configurer.buildConfiguration().commandBus();
//		CommandGateway commandGateway = DefaultCommandGateway.builder().commandBus(commandBus).build();
//
//		return commandGateway;
//	}
//	
//	@Bean
//	@Qualifier("retryCommandgateway")
//	public CommandGateway commandGateway() {
//
//		Configurer configurer = DefaultConfigurer.defaultConfiguration();
//		CommandBus commandBus = configurer.buildConfiguration().commandBus();
//		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//		RetryScheduler rs = CustomIntervalRetryScheduler.builder().retryExecutor(scheduledExecutorService)
//				.maxRetryCount(4)
//				.retryInterval(1000).build();
//		CommandGateway commandGateway = DefaultCommandGateway.builder().commandBus(commandBus).retryScheduler(rs)
//				.build();
//
//		return commandGateway;
//	}
//}
