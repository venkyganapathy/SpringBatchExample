package com.profinch.fincluez.casaTransformation.batch.step2;

import com.profinch.fincluez.casaTransformation.model.CasaTransformationModel;
import com.profinch.fincluez.casaTransformation.repo.CasaMartRepo;
import com.profinch.fincluez.casaTransformation.repo.CasaTransformationQueueRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class CasaTransformationWriter implements ItemWriter<CasaTransformationModel> {

    private Logger log = LoggerFactory.getLogger(CasaTransformationWriter.class);

    private CasaMartRepo casaMartRepo;
    private CasaTransformationQueueRepo casaTransformationQueueRepo;

    @Autowired
    public CasaTransformationWriter(CasaMartRepo casaMartRepo,
                                    CasaTransformationQueueRepo casaTransformationQueueRepo) {
        this.casaMartRepo = casaMartRepo;
        this.casaTransformationQueueRepo = casaTransformationQueueRepo;
    }

    @Override
    public void write(List<? extends CasaTransformationModel> list) throws Exception {
        log.debug("Inside....Writer...with number of Model Records....{}",list.size());
        Thread.sleep(1000);
        for (CasaTransformationModel casaTransformationModel : list){
            log.debug("Repo Saving Mart {}", casaTransformationModel.getCasaMart());
            casaMartRepo.save(casaTransformationModel.getCasaMart());

            log.debug("Repo Saving Queue {}", casaTransformationModel.getCasaTransformationQueue());
            casaTransformationQueueRepo.save(casaTransformationModel.getCasaTransformationQueue());
        }
        log.debug("End of Writer");
    }
}
