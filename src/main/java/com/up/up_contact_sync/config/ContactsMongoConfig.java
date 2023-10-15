package com.up.up_contact_sync.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.up.up_contact_sync.service.utils.UPLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactsMongoConfig {
    @Value("${CONTACTS_DB_NAME}")
    private String contactsDbName;

    @Value("${CONTACT_SERVICE_DATABASE_URL}")
    private String contactServiceDatabaseUrl;

    @Bean
    public MongoClient mongoClient() {
        UPLogger.log().info("{} mongoClient: {}", this.getClass().getSimpleName(),
                contactsDbName);
        ConnectionString connectionString = new ConnectionString(contactServiceDatabaseUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}
