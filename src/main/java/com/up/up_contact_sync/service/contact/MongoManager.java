package com.up.up_contact_sync.service.contact;

import com.up.up_contact_sync.service.utils.UPLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class MongoManager {
    private final MongoTemplate mongoTemplate;
    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations gridFsOperations;


    public void saveData(String encryptedData, String token) {
        var query = new Query(Criteria.where("filename").is(token));
        mongoTemplate.remove(query, "user_contacts");

        var resource = gridFsTemplate.getResource(token);
        UPLogger.log().info("{} saveData: {}", this.getClass().getSimpleName(), resource );

        gridFsOperations.store(new ByteArrayInputStream(encryptedData.getBytes(StandardCharsets.UTF_8)), token);

    }

    public GridFsResource getFile(String token) {
        return gridFsTemplate.getResource(token);
    }
}
