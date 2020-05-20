package com.profinch.fincluez.casaTransformation.repo;

import com.profinch.fincluez.casaTransformation.constants.ProcessStatus;
import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueueCK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CasaTransformationQueueRepo extends JpaRepository<CasaTransformationQueue, CasaTransformationQueueCK> {

    List<CasaTransformationQueue> findByTransformationProcessStatus(ProcessStatus transformationProcessStatus);
}
