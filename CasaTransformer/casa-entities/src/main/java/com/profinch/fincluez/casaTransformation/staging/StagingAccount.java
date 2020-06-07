package com.profinch.fincluez.casaTransformation.staging;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@IdClass(StagingAccountCK.class)
//@Table(name= "staging_account", schema = "fincluez_staging")
public class StagingAccount implements Serializable {

    @Id
    private String entityCode;
    @Id
    private String branchCode;
    @Id
    private String customerAccountNumber;
    @Id
    private Date elRunDate;

    private String columnOne;
    private String columnTwo;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
