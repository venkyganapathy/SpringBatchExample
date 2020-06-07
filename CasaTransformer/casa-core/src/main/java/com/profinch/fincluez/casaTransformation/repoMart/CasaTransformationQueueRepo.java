package com.profinch.fincluez.casaTransformation.repoMart;

import com.profinch.fincluez.casaTransformation.constants.ProcessStatus;
import com.profinch.fincluez.casaTransformation.mart.CasaTransformationQueue;
import com.profinch.fincluez.casaTransformation.mart.CasaTransformationQueueCK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaTransformationQueueRepo extends JpaRepository<CasaTransformationQueue, CasaTransformationQueueCK> {

    List<CasaTransformationQueue> findByTransformationProcessStatus(ProcessStatus transformationProcessStatus);
}
