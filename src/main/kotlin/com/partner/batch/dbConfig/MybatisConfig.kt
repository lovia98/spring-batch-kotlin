package com.partner.batch.dbConfig

import org.apache.ibatis.session.SqlSessionFactory
import org.apache.tomcat.jdbc.pool.DataSource
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.transaction.annotation.EnableTransactionManagement


interface MybatisDbConfig : DbConfig {

    fun sqlSessionFactory(dataSource: DataSource) : SqlSessionFactory
    fun sessionTemplate(sqlSessionFactory: SqlSessionFactory) : SqlSessionTemplate
}

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = ["com.partner.batch.repository"], annotationClass = OrdersDB::class, sqlSessionFactoryRef = "ordersSqlSessionFactory")
class OrdersDbConfig : MybatisDbConfig  {

    @Autowired
    private lateinit var prop : OrdersDbProperty

    @Autowired
    private lateinit var applicationContext : ApplicationContext

    @Autowired
    private lateinit var resourceLoader: ResourceLoader


    @Bean(name = ["orderDataSource"])
    override fun dataSource() : DataSource {

        var dataSource = DataSource()

        dataSource.url = prop.url
        dataSource.username = prop.username
        dataSource.password = prop.password
        dataSource.driverClassName = prop.driverClassName

        return dataSource
    }

    @Bean(name=["ordersSqlSessionFactory"])
    override fun sqlSessionFactory(
            @Qualifier("orderDataSource") dataSource: DataSource
    ): SqlSessionFactory {

        var sqlSessionFactory = SqlSessionFactoryBean()
        sqlSessionFactory.setDataSource(dataSource)

        sqlSessionFactory.setTypeAliasesPackage("com.partner.batch.models")
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"))
        sqlSessionFactory.setConfigLocation(resourceLoader.getResource("classpath:mybatis/mybatis-config.xml"))

        return sqlSessionFactory.`object`!!
    }

    @Bean(name=["ordersSqlSessionTemplate"])
    override fun sessionTemplate(
            @Qualifier("ordersSqlSessionFactory") sqlSessionFactory: SqlSessionFactory ): SqlSessionTemplate {

        return SqlSessionTemplate(sqlSessionFactory)
    }
}