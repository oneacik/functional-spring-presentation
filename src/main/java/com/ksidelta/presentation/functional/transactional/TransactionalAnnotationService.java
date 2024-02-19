package com.ksidelta.presentation.functional.transactional;

import com.ksidelta.presentation.functional.SimpleEntity;
import com.ksidelta.presentation.functional.transactional.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Primary
public class TransactionalAnnotationService implements TransactionalService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    @Transactional(rollbackFor = IOException.class)
    public void runInTransaction() throws Exception {
        final var simpleEntity = new SimpleEntity("A", "TEST");

        mongoTemplate.save(simpleEntity);
        throw new IOException();
    }
}
