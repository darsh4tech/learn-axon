package com.iw.demo.domain;


import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AxonConfig {

//	@Autowired
//	private final SimpleEventHandlerInvoker simpleEventHandlerInvoker;

	
	@Value("${spring.data.mongodb.host:127.0.0.1}")
	private String mongoHost;

	@Value("${spring.data.mongodb.port:27017}")
	private int mongoPort;

	@Value("${spring.data.mongodb.database:testEvent}")
	private String mongoDatabase;

//	@Bean
//	public MongoSagaStore sagaStore() {
//		return MongoSagaStore.builder().mongoTemplate(axonMongoTemplate()).build();
//	}
//
//	@Bean
//	public TokenStore tokenStore() {
//		return MongoTokenStore.builder().mongoTemplate(axonMongoTemplate())
//				.serializer(JacksonSerializer.defaultSerializer()).build();
//	}

    @Bean
    public EventStorageEngine eventStorageEngine() {
    	return MongoEventStorageEngine.builder().mongoTemplate(axonMongoTemplate()).build(); 
    }

	@Bean
	public MongoTemplate axonMongoTemplate() {
		return DefaultMongoTemplate.builder().mongoDatabase(mongo()).build();
	}

	@Bean
	public MongoClient mongo() {
		final ConnectionString connectionString = new ConnectionString(
				"mongodb://" + mongoHost + ":" + mongoPort +"/"+ mongoDatabase);
		final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString).build();
		return MongoClients.create(mongoClientSettings);
	}
}