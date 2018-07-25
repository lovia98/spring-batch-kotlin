package com.partner.batch.batchConfig

import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.stereotype.Component

@Component
class StepListener : StepExecutionListener {

    private val log = LoggerFactory.getLogger(StepListener::class.java)

    override fun beforeStep(stepExecution: StepExecution?) {
        val name = stepExecution?.stepName
        log.info("StepStart => Name: ${name}")
    }

    override fun afterStep(stepExecution: StepExecution?): ExitStatus {

        val name = stepExecution?.stepName
        val readCount = stepExecution?.readCount ?:0
        val writeCount = stepExecution?.writeCount ?:0
        val skipCount = stepExecution?.skipCount ?:0

        log.info("StepName Finished => Name : ${name}, ReadCount : ${readCount}, WriteCount : ${writeCount}, SkipCount : ${skipCount}")

        return ExitStatus("COMPLETED")
    }

}