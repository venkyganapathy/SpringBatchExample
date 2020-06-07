package com.profinch.fincluez.casaTransformation.batch.step1;

import com.profinch.fincluez.casaTransformation.constants.ProcessStatus;
import com.profinch.fincluez.casaTransformation.core.CasaTransformationBL;
import com.profinch.fincluez.casaTransformation.mart.CasaTransformationQueue;
import com.profinch.fincluez.casaTransformation.staging.StagingAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
@StepScope
public class CasaQueuePopulatorProcessor implements ItemProcessor<StagingAccount, CasaTransformationQueue> {

    private Logger log = LoggerFactory.getLogger(CasaQueuePopulatorProcessor.class);

    private CasaTransformationBL casaTransformationBL;

    @Autowired
    public CasaQueuePopulatorProcessor(CasaTransformationBL casaTransformationBL) {
        this.casaTransformationBL = casaTransformationBL;
    }

    @Override
    public CasaTransformationQueue process(StagingAccount stagingAccount) throws Exception {

        log.debug("Inside...Populator Processor...with EntityCode {}",stagingAccount.getEntityCode());
        log.debug("Inside...Populator Processor...with BranchCode {}",stagingAccount.getBranchCode());
        log.debug("Inside...Populator Processor...with ELRunDate {}",stagingAccount.getElRunDate());
        log.debug("Inside...Populator Processor...with AccountNumber {}", stagingAccount.getCustomerAccountNumber());
        Thread.sleep(1000);
        CasaTransformationQueue casaTransformationQueue = new CasaTransformationQueue();
        casaTransformationQueue.setBranchCode(stagingAccount.getBranchCode());
        casaTransformationQueue.setCustomerAccountNumber(stagingAccount.getCustomerAccountNumber());
        casaTransformationQueue.setElRunDate(stagingAccount.getElRunDate());
        casaTransformationQueue.setEntityCode(stagingAccount.getEntityCode());
        casaTransformationQueue.setTransformationProcessStatus(ProcessStatus.UNPROCESSED);
        casaTransformationQueue.setQueuedTimeStamp(new Timestamp(new Date().getTime()));
        log.debug("Populator Processor Exiting for {}",casaTransformationQueue.getCustomerAccountNumber());

        return casaTransformationQueue;
    }
}
