package com.profinch.fincluez.casaTransformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories({"com.profinch.fincluez.casaTransformation.repoStaging","com.profinch.fincluez.casaTransformation.repoMart"})
//@EntityScan({"com.profinch.fincluez.casaTransformation.mart","com.profinch.fincluez.casaTransformation.staging"})
public class CasaTransformationApplication{

    private static Logger log = LoggerFactory.getLogger(CasaTransformationApplication.class);

    public static void main(String[] args) {
    	log.debug("inside CasaTransformationApplication application....Starting the Batch Run");
        SpringApplication.run(CasaTransformationApplication.class, args);
    }

}
