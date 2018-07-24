package com.partner.batch.dbConfig

import org.apache.tomcat.jdbc.pool.DataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.transaction.annotation.EnableTransactionManagement

interface DbConfig {
    fun dataSource() : DataSource
}

@Configuration
@EnableTransactionManagement
class BatchDbConfig : DbConfig {

    @Autowired
    private lateinit var prop : BatchDbProperty

    @Primary
    @Bean(name = ["batchDataSource"])
    override fun dataSource() : DataSource {

        var dataSource = DataSource()

        dataSource.url = prop.url
        dataSource.username = prop.username
        dataSource.password = prop.password
        dataSource.driverClassName = prop.driverClassName

        return dataSource
    }
}