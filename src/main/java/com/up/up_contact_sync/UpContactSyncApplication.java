package com.up.up_contact_sync;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpContactSyncApplication {
    public static void main(String[] args) {
        ElasticApmAttacher.attach();
        SpringApplication.run(UpContactSyncApplication.class, args);
    }

}
