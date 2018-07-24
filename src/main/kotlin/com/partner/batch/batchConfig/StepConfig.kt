package com.partner.batch.batchConfig

import com.partner.batch.models.OrderInfo
import com.partner.batch.models.Person
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.tomcat.jdbc.pool.DataSource
import org.mybatis.spring.batch.MyBatisCursorItemReader
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
class StepConfig {

    private val log = LoggerFactory.getLogger(StepConfig::class.java)

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Bean
    fun reader(
            @Qualifier("ordersSqlSessionFactory") sqlSessionFactory: SqlSessionFactory) : MyBatisCursorItemReader<OrderInfo> {

        val reader = MyBatisCursorItemReader<OrderInfo>()

        reader.setSqlSessionFactory(sqlSessionFactory)
        reader.setQueryId("com.partner.batch.repository.OrdersMapper.findOrders")

        return reader
    }

//    @Bean
//    fun processor() : ItemProcessor<OrderInfo, OrderInfo> {
//
//        return object : ItemProcessor<OrderInfo, OrderInfo> {
//
//            override fun process(order: OrderInfo?): OrderInfo? {
//
//                log.info("processing orderInfo [${order}]")
//
//                return order
//            }
//        }
//    }

    @Bean
    fun writer(@Value("\${export.filePath}") path: String) : FlatFileItemWriter<OrderInfo> {

        return FlatFileItemWriterBuilder<OrderInfo>()
                .name("jsonFile-writer")
                .encoding("UTF-8")
                .resource(FileSystemResource(makeFilePath(path)))
                .shouldDeleteIfExists(true)
                .headerCallback { writer ->  writer.write("{ \"orderList\" : [")  }
                .lineAggregator(jsonLineAggregator<OrderInfo>())
                .footerCallback{ writer -> writer.write("]}")}
                .build()
    }

    @Bean
    fun step1(
            reader: MyBatisCursorItemReader<OrderInfo>,
            writer: FlatFileItemWriter<OrderInfo>,
            listener: StepListener
    ) : Step {
        return stepBuilderFactory.get("step1")
                .chunk<OrderInfo, OrderInfo>(10)
                .reader(reader)
//                .processor(processor())
                .writer(writer)
                .listener(listener)
                .build()
    }

}