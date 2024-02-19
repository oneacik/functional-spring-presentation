package com.ksidelta.presentation.functional.transactional;

import com.ksidelta.presentation.functional.SimpleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;

@Service
//@Primary
public class FunctionalWrappedExceptionService implements TransactionalService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;

    // Explain how transactional works
    public void runInTransaction() throws Exception {
        final var simpleEntity = new SimpleEntity("A", "CONTENT");

        WrappedException.unwrap(() ->
                transactionTemplate.execute((status) ->
                        WrappedException.wrap(() -> {
                                    mongoTemplate.save(simpleEntity);
                                    throw new IOException();
                                }
                        )
                )
        );
    }


    static public class WrappedException extends RuntimeException {
        Exception wrappedException;

        public WrappedException(Exception e) {
            super(e);
            wrappedException = e;
        }

        static public <T> T wrap(GenericFunction<T> function) {
            try {
                return function.execute();
            } catch (Exception e) {
                if (e instanceof RuntimeException)
                    throw (RuntimeException) e;
                throw new WrappedException(e);
            }
        }

        static public <T> T unwrap(GenericFunction<T> function) throws Exception {
            try {
                return function.execute();
            } catch (Exception e) {
                if (e instanceof WrappedException) {
                    throw ((WrappedException) e).wrappedException;
                }
                throw e;
            }
        }

    }

    public interface GenericFunction<T> {
        public T execute() throws Exception;
    }
}
