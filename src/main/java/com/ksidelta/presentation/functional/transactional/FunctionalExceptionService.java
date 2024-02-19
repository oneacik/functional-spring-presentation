package com.ksidelta.presentation.functional.transactional;

import com.ksidelta.presentation.functional.SimpleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
// @Primary
public class FunctionalExceptionService implements TransactionalService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;

    public void runInTransaction() throws Exception {
        final var simpleEntity = new SimpleEntity("A", "CONTENT");

        transactionTemplate.execute((action) -> {
            mongoTemplate.save(simpleEntity);

            throw new RuntimeException();
        });
    }
}
