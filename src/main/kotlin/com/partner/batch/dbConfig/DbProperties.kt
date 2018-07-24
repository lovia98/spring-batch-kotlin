package com.partner.batch.dbConfig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

open class DataSourceProperties {
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String
    lateinit var driverClassName: String
}

@Component
@ConfigurationProperties("spring.datasource.db-orders")
class OrdersDbProperty : DataSourceProperties()

@Component
@ConfigurationProperties("spring.datasource.db-batch")
class BatchDbProperty : DataSourceProperties()
