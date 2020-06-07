package com.profinch.fincluez.casaTransformation.mart;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@SequenceGenerator(name="account_mart_id_seq")
//@Table(name= "casa_mart", schema = "fincluez_mart")
public class CasaMart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="account_mart_id_seq")
    private Long accountMartId;
    private String entityCode;
    private String branchCode;
    private String customerAccountNumber;
    private Date elRunDate;
    private String comments;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
