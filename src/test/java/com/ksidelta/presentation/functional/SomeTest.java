package com.ksidelta.presentation.functional;

import com.ksidelta.presentation.functional.transactional.TransactionalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@EnableMongoRepositories
public class SomeTest {
    @Autowired
    TransactionalService simpleService;

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    public void tearDown() {
        mongoTemplate.findAll(SimpleEntity.class).forEach(mongoTemplate::remove);
    }

    @Test
    public void throwsException() throws Exception {
        assertThrows(Exception.class, () -> simpleService.runInTransaction());

        Assertions.assertNull(mongoTemplate.findById("A", SimpleEntity.class));
    }


    @Test
    public void throwsCheckedException() {
        assertThrows(IOException.class, () -> simpleService.runInTransaction());

        Assertions.assertNull(mongoTemplate.findById("A", SimpleEntity.class));
    }
}
