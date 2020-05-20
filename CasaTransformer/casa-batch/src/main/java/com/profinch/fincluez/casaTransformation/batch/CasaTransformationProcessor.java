package com.profinch.fincluez.casaTransformation.batch;

import com.profinch.fincluez.casaTransformation.constants.ProcessStatus;
import com.profinch.fincluez.casaTransformation.core.CasaTransformationBL;
import com.profinch.fincluez.casaTransformation.mart.CasaMart;
import com.profinch.fincluez.casaTransformation.model.CasaTransformationModel;
import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
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
public class CasaTransformationProcessor implements ItemProcessor<CasaTransformationQueue, CasaTransformationModel> {

    private Logger log = LoggerFactory.getLogger(CasaTransformationProcessor.class);

    private CasaTransformationBL casaTransformationBL;

    @Autowired
    public CasaTransformationProcessor(CasaTransformationBL casaTransformationBL) {
        this.casaTransformationBL = casaTransformationBL;
    }

    @Override
    public CasaTransformationModel process(CasaTransformationQueue casaTransformationQueue) throws Exception {

        log.debug("Inside...Processor...with ELRunDate {}", casaTransformationQueue.getElRunDate());
        log.debug("Inside...Processor...with AccountNumber {}", casaTransformationQueue.getAccountNumber());

        CasaTransformationModel casaTransformationModel = new CasaTransformationModel();

        casaTransformationQueue.setTransformationProcessStatus(ProcessStatus.PROCESSED);
        casaTransformationQueue.setProcessedTimeStamp(new Timestamp(new Date().getTime()));

        CasaMart casaMart = casaTransformationBL.doTransformation(casaTransformationQueue);

        casaTransformationModel.setCasaTransformationQueue(casaTransformationQueue);
        casaTransformationModel.setCasaMart(casaMart);

        return casaTransformationModel;
    }
}
