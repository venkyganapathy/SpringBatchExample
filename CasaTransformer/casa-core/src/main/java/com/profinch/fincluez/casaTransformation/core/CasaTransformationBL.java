package com.profinch.fincluez.casaTransformation.core;

import com.profinch.fincluez.casaTransformation.mart.CasaMart;
import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class CasaTransformationBL {
    private Logger log = LoggerFactory.getLogger(CasaTransformationBL.class);

    public CasaMart doTransformation(CasaTransformationQueue casaTransformationQueue) throws InterruptedException {
        log.debug("Inside....AccountTransformationBL--doTransformation....with Account {}", casaTransformationQueue.getAccountNumber());

        CasaMart casaMart = new CasaMart();
        casaMart.setAccountNumber(casaTransformationQueue.getAccountNumber());
        casaMart.setElRunDate(casaTransformationQueue.getElRunDate());
        casaMart.setComments("Done with the Transformation at Time -- "+new Timestamp(new Date().getTime()));

        log.debug("Transformation Process in Progress....");
        Thread.sleep(10000);
        log.debug("Done with Transformation...exiting");

        return casaMart;
    }
}
