package com.ksidelta.presentation.functional

import com.ksidelta.presentation.functional.transactional.TransactionalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import java.io.IOException

@Service
//@Primary
class SimpleServiceKotlin : TransactionalService {
    @Autowired
    var mongoTemplate: MongoTemplate? = null

    @Autowired
    var transactionTemplate: TransactionTemplate? = null

    // Explain how transactional works
    @Throws(IOException::class)
    override fun runInTransaction() {
        val simpleEntity = SimpleEntity("A", "CONTENT")

        transactionTemplate!!.execute {
            mongoTemplate!!.save(simpleEntity)
            throw IOException()
        }
    }
}
