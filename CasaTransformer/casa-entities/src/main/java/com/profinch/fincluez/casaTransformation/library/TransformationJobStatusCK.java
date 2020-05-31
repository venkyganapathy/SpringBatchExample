package com.profinch.fincluez.casaTransformation.library;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class TransformationJobStatusCK implements Serializable {
    private String jobName;
    private String stepName;
    @Temporal(TemporalType.DATE)
    private Date elRunDate;

}
