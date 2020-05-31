package com.profinch.fincluez.casaTransformation.repo;

import com.profinch.fincluez.casaTransformation.library.TransformationJobStatus;
import com.profinch.fincluez.casaTransformation.library.TransformationJobStatusCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Date;
import java.util.List;

public interface TransformationJobStatusRepo extends JpaRepository<TransformationJobStatus, TransformationJobStatusCK> {
    @QueryHints(@javax.persistence.QueryHint(name="org.hibernate.fetchSize", value="1"))
    public List<TransformationJobStatus> findByJobNameAndStepNameOrderByElRunDateDesc(String jobName,String stepName);
    public List<TransformationJobStatus> findByJobNameAndStepNameAndElRunDate(String jobName, String stepName, Date elRunDate);
}
