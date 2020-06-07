package com.profinch.fincluez.casaTransformation.batch.step0;

import com.profinch.fincluez.casaTransformation.config.JobName;
import com.profinch.fincluez.casaTransformation.mart.TransformationJobStatus;
import com.profinch.fincluez.casaTransformation.repoMart.TransformationJobStatusRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class ValidationTaskletListener implements StepExecutionListener {
    private Logger log = LoggerFactory.getLogger(ValidationTaskletListener.class);

    @Autowired
    private TransformationJobStatusRepo transformationJobStatusRepo;

    @Override
    @BeforeStep
    public void beforeStep(StepExecution stepExecution)
    {
        log.debug("********* BEFORE STEP-0");
        Date elRunDate = stepExecution.getJobParameters().getDate("elRunDate");
        log.debug("********* BEFORE STEP-0 ...elRunDate is {}",elRunDate);

    }

    @Override
    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("********* AFTER STEP-0");
        String exitStatusString;
        Date elRunDate = stepExecution.getJobParameters().getDate("elRunDate");
        log.debug("********* AFTER STEP-0....with EL-Run-Date as {}",elRunDate);

        // Persistence in TJStatus
        TransformationJobStatus transformationJobStatus = new TransformationJobStatus();
        transformationJobStatus.setJobName(JobName.CASA_TRANSFORMATION_JOB.toString());
        transformationJobStatus.setStepName(JobName.CASA_TRANSFORMATION_VALIDATION_STEP.toString());
        transformationJobStatus.setElRunDate(elRunDate);
        transformationJobStatus.setTimestamp(new Timestamp(System.currentTimeMillis()));
        transformationJobStatusRepo.save(transformationJobStatus);

        // To decide whether to Skip Step-1
        List<TransformationJobStatus> transformationJobStatusList
                = transformationJobStatusRepo.findByJobNameAndStepNameAndElRunDate
                (JobName.CASA_TRANSFORMATION_JOB.toString()
                        ,JobName.CASA_TRANSFORMATION_QUEUE_POPULATOR_STEP.toString()
                        ,elRunDate);

        log.debug("********* AFTER STEP-0 --> got TJStatus as {}",transformationJobStatusList.toString());

        if (transformationJobStatusList!=null
            && !transformationJobStatusList.isEmpty()
                && transformationJobStatusList.size()>0){
            exitStatusString = "skip-Step-1";
        } else{
            exitStatusString = "run-Step-1";
        }
        log.debug("********* AFTER STEP-0 - exitStatusString is {}",exitStatusString);
        return new ExitStatus(exitStatusString);
    }
}
