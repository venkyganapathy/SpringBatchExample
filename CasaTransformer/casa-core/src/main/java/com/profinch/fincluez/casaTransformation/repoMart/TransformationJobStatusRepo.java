package com.profinch.fincluez.casaTransformation.repoMart;

import com.profinch.fincluez.casaTransformation.mart.TransformationJobStatus;
import com.profinch.fincluez.casaTransformation.mart.TransformationJobStatusCK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransformationJobStatusRepo extends JpaRepository<TransformationJobStatus, TransformationJobStatusCK> {
    //@QueryHints(@javax.persistence.QueryHint(name="org.hibernate.fetchSize", value="1"))
    public List<TransformationJobStatus> findTop1ByJobNameAndStepNameOrderByElRunDateDesc(String jobName,String stepName);
    public List<TransformationJobStatus> findByJobNameAndStepNameAndElRunDate(String jobName, String stepName, Date elRunDate);
}
