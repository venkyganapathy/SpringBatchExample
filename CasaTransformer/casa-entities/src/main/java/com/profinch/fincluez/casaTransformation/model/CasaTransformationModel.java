package com.profinch.fincluez.casaTransformation.model;

import com.profinch.fincluez.casaTransformation.mart.CasaMart;
import com.profinch.fincluez.casaTransformation.mart.CasaTransformationQueue;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

@Getter
@Setter
public class CasaTransformationModel implements Serializable {

    private CasaTransformationQueue casaTransformationQueue;
    private CasaMart casaMart;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
