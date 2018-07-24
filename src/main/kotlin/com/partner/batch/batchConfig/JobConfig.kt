package com.partner.batch.batchConfig

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime


@Configuration
@EnableBatchProcessing
@EnableScheduling
class BatchJobConfiguration {

    private val log = LoggerFactory.getLogger(BatchJobConfiguration::class.java)

    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var step1: Step

    @Autowired
    private lateinit var jobLauncher: SimpleJobLauncher

    @Scheduled(cron ="0 30 0/1 * * *")
    //@Scheduled(cron ="0/10 * * * * *")
    fun perform() {

        log.info("OrdersBatchJob Start")

        val jobId = System.currentTimeMillis().toString()
        val param = JobParametersBuilder().addString("JobID", jobId ).toJobParameters()
        val execution = jobLauncher.run(ordersBatchJob(), param)

        log.info("Schedule finished with status: "+ execution.status)
    }

    @Bean
    fun ordersBatchJob() : Job {
        return jobBuilderFactory.get("orders Batch Job")
                .incrementer(RunIdIncrementer())
                .flow(step1)
                .end()
                .build()
    }
}

@Configuration
@EnableScheduling
class BatchScheduler {

    @Bean
    fun transactionManager(): ResourcelessTransactionManager {
        return ResourcelessTransactionManager()
    }

    @Bean
    fun mapJobRepositoryFactory(
            txManager: ResourcelessTransactionManager): MapJobRepositoryFactoryBean {

        val factory = MapJobRepositoryFactoryBean(txManager)
        factory.afterPropertiesSet()
        return factory
    }

    @Bean
    fun jobRepository(factory: MapJobRepositoryFactoryBean): JobRepository {
        return factory.`object` as JobRepository
    }

    @Bean
    fun jobLauncher(jobRepository: JobRepository): SimpleJobLauncher {
        val launcher = SimpleJobLauncher()
        launcher.setJobRepository(jobRepository)
        return launcher
    }
}