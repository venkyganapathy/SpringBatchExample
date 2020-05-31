package com.profinch.fincluez.casaTransformation.library;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@IdClass(TransformationJobStatusCK.class)
public class TransformationJobStatus implements Serializable {

    @Id
    private String jobName;
    @Id
    private String stepName;
    @Temporal(TemporalType.DATE)
    @Id
    private Date elRunDate;
    private Timestamp timestamp;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
