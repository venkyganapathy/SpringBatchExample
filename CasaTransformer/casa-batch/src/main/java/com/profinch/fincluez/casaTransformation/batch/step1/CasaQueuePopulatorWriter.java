package com.profinch.fincluez.casaTransformation.batch.step1;

import com.profinch.fincluez.casaTransformation.repo.CasaTransformationQueueRepo;
import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class CasaQueuePopulatorWriter implements ItemWriter<CasaTransformationQueue> {

    private Logger log = LoggerFactory.getLogger(CasaQueuePopulatorWriter.class);

    private CasaTransformationQueueRepo casaTransformationQueueRepo;

    @Autowired
    public CasaQueuePopulatorWriter(CasaTransformationQueueRepo casaTransformationQueueRepo) {
        this.casaTransformationQueueRepo = casaTransformationQueueRepo;
    }

    @Override
    public void write(List<? extends CasaTransformationQueue> list) throws Exception {
        log.debug("Inside....Queue Populator Writer...with number of Model Records....{}",list.size());
        Thread.sleep(1000);
        for (CasaTransformationQueue casaTransformationQueue : list){
            log.debug("Repo Saving CasaTransformationQueue {}", casaTransformationQueue.getCustomerAccountNumber());
            casaTransformationQueueRepo.save(casaTransformationQueue);
        }
        log.debug("End of Queue Populator Writer");
    }
}
