package com.profinch.fincluez.casaTransformation.batch.step0;

import com.profinch.fincluez.casaTransformation.batch.step1.CasaQueuePopulatorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ValidationTasklet implements Tasklet {
    private Logger log = LoggerFactory.getLogger(ValidationTasklet.class);

    @Value("#{jobParameters['entityCode']}")
    private String entityCode;

    @Value("#{jobParameters['branchCode']}")
    private String branchCode;

    @Value("#{jobParameters['elRunDate']}")
    private String elRunDate;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.debug("In Tasklet Step 0 with entityCode {}",entityCode);
        log.debug("In Tasklet Step 0 with branchCode {}",branchCode);
        log.debug("In Tasklet Step 0 with elRunDate {}",elRunDate);
        return RepeatStatus.FINISHED;
    }
}
