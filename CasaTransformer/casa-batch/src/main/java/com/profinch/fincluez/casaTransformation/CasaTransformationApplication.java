package com.profinch.fincluez.casaTransformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.profinch.fincluez.casaTransformation.repo"})
@EntityScan({"com.profinch.fincluez.casaTransformation"})
public class CasaTransformationApplication {

	private static Logger log = LoggerFactory.getLogger(CasaTransformationApplication.class);

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CasaTransformationApplication.class);
    }

    public static void main(String[] args) {
    	log.debug("inside CasaTransformationApplication application....Starting the Batch Run");
        SpringApplication.run(CasaTransformationApplication.class, args);
    }

}
