package com.profinch.fincluez.casaTransformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories({"com.profinch.fincluez.casaTransformation.repo"})
@EntityScan({"com.profinch.fincluez.casaTransformation"})
public class CasaTransformationApplication{

    private static Logger log = LoggerFactory.getLogger(CasaTransformationApplication.class);

    public static void main(String[] args) {
    	log.debug("inside CasaTransformationApplication application....Starting the Batch Run");
        SpringApplication.run(CasaTransformationApplication.class, args);
    }

}
