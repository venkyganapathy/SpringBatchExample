package com.profinch.fincluez.casaTransformation.config;

import com.profinch.fincluez.casaTransformation.batch.CasaTransformationProcessor;
import com.profinch.fincluez.casaTransformation.batch.CasaTransformationWriter;
import com.profinch.fincluez.casaTransformation.model.CasaTransformationModel;
import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@EnableBatchProcessing
@Configuration
public class CasaTransformationJobConfig {
	private Logger log = LoggerFactory.getLogger(CasaTransformationJobConfig.class);

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;


	@Autowired
	private CasaTransformationProcessor casaTransformationProcessor;
	@Autowired
	private CasaTransformationWriter casaTransformationWriter;
	@Autowired
    private ItemReader casaTransformationCustomReader;

	public CasaTransformationJobConfig() {
		log.info("Creating beans for FinCluez CasaTransformation BatchProcessorJob!!");
	}

	@Bean
	public TaskExecutor taskExecutor(){
		return new SimpleAsyncTaskExecutor("FinCluez-CasaTransformer-");
	}

	@Bean
    Step casaTransformationBatchProcessStep() {
		log.debug("Creating casaTransformationBatchProcessStep");
		return stepBuilderFactory.get("casaTransformationBatchProcessStep")
				.<CasaTransformationQueue, CasaTransformationModel>chunk(5)
				.reader(casaTransformationCustomReader)
				.processor(casaTransformationProcessor)
				.writer(casaTransformationWriter)
				.taskExecutor(taskExecutor())
				.throttleLimit(3)
				.build();
	}
	
	@Bean
	public Job casaTransformationBatchProcessJob(@Qualifier("casaTransformationBatchProcessStep") Step casaTransformationBatchProcessStep) {
		log.debug("Creating casaTransformationBatchProcessJob");
		return jobBuilderFactory.get("casaTransformationBatchProcessJob")
				.incrementer(new RunIdIncrementer())
				.flow(casaTransformationBatchProcessStep())
				.end()
				.build();
	}

}