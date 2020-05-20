package com.profinch.fincluez.casaTransformation.staging;

import com.profinch.fincluez.casaTransformation.constants.ProcessStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@IdClass(CasaTransformationQueueCK.class)
public class CasaTransformationQueue implements Serializable {

    @Id
    private String accountNumber;
    @Id
    private Date elRunDate;
    private String branchCode;
    @Enumerated(EnumType.STRING)
    private ProcessStatus transformationProcessStatus;
    private Timestamp processedTimeStamp;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
