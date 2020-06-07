package com.profinch.fincluez.casaTransformation.mart;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
public class CasaTransformationQueueCK implements Serializable {

    private String entityCode;
    private String branchCode;
    private String customerAccountNumber;
    private Date elRunDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
