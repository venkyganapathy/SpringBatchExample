package com.profinch.fincluez.casaTransformation.config;

import com.profinch.fincluez.casaTransformation.batch.step1.CasaQueuePopulatorProcessor;
import com.profinch.fincluez.casaTransformation.batch.step1.CasaQueuePopulatorWriter;
import com.profinch.fincluez.casaTransformation.batch.step2.CasaTransformationProcessor;
import com.profinch.fincluez.casaTransformation.batch.step2.CasaTransformationWriter;
import com.profinch.fincluez.casaTransformation.model.CasaTransformationModel;
import com.profinch.fincluez.casaTransformation.mart.CasaTransformationQueue;
import com.profinch.fincluez.casaTransformation.staging.StagingAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
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

	//Step-0
	@Autowired
	private Tasklet validationTasklet;
	@Autowired
	private StepExecutionListener validationTaskletListener;

	//Step-1
	@Autowired
	private ItemReader casaQueuePopulatorPagingReader;
	@Autowired
	private ItemReader casaQueuePopulatorCursorReader;
	@Autowired
	private CasaQueuePopulatorProcessor casaQueuePopulatorProcessor;
	@Autowired
	private CasaQueuePopulatorWriter casaQueuePopulatorWriter;
	@Autowired
	StepExecutionListener casaQueuePopulatorListener;

	//Step-2
	@Autowired
	private ItemReader casaTransformationPagingReader;
	@Autowired
	private ItemReader casaTransformationCursorReader;
	@Autowired
	private CasaTransformationProcessor casaTransformationProcessor;
	@Autowired
	private CasaTransformationWriter casaTransformationWriter;
	@Autowired
	StepExecutionListener casaTransformationListener;

	//Job Listener
	@Autowired
	JobExecutionListener casaTransformationJobListener;


	public CasaTransformationJobConfig() {
		log.info("Creating beans for FinCluez CasaTransformation BatchProcessorJob!!");
	}

	@Bean
	public TaskExecutor taskExecutor(){
		return new SimpleAsyncTaskExecutor("FinCluez-CasaTransformer-");
	}

	//Step-0
	@Bean
	Step validationTaskletStep() {
		return stepBuilderFactory.get("validationTaskletStep")
				.tasklet(validationTasklet)
				.listener(validationTaskletListener)
				.build();
	}

	//Step-1
	@Bean
	Step casaTransformationQueuePopulatorStep() {
		log.debug("Creating casaTransformationQueuePopulatorStep");
		SimpleStepBuilder builder =
				(SimpleStepBuilder) stepBuilderFactory.get("casaTransformationQueuePopulatorStep")
						.<StagingAccount, CasaTransformationQueue>chunk(5)
						.reader(casaQueuePopulatorPagingReader)
						.processor(casaQueuePopulatorProcessor)
						.writer(casaQueuePopulatorWriter)
						.taskExecutor(taskExecutor())
						.throttleLimit(2);
		builder.listener((StepExecutionListener) casaQueuePopulatorListener);
		return builder.build();
	}

	//Step-2
	@Bean
    Step casaTransformationStep() {
		log.debug("Creating casaTransformationStep");
        SimpleStepBuilder builder = (SimpleStepBuilder) stepBuilderFactory.get("casaTransformationStep")
			.<CasaTransformationQueue, CasaTransformationModel>chunk(5)
			.reader(casaTransformationPagingReader)
			.processor(casaTransformationProcessor)
			.writer(casaTransformationWriter)
			.taskExecutor(taskExecutor())
			.throttleLimit(2);
        builder.listener((StepExecutionListener) casaTransformationListener);
        return builder.build();
	}


	
	@Bean
	public Job casaTransformationBatchProcessJob(
			@Qualifier("validationTaskletStep") Step validationTaskletStep,
			@Qualifier("casaTransformationQueuePopulatorStep") Step casaTransformationQueuePopulatorStep,
			@Qualifier("casaTransformationStep") Step casaTransformationStep
			) {
		log.debug("Creating casaTransformationBatchProcessJob");
		return jobBuilderFactory.get("casaTransformationBatchProcessJob")
        .incrementer(new RunIdIncrementer())

		//Step-0 to Step-1
		.start(validationTaskletStep)
				.on("run-Step-1")
				.to(casaTransformationQueuePopulatorStep)

		.from(validationTaskletStep).on("skip-Step-1")
				.to(casaTransformationStep)

		.from(casaTransformationQueuePopulatorStep)
				.on("*")
				.to(casaTransformationStep)

		//Step-0 to Step-2


		//.next(casaTransformationQueuePopulatorStep)
		//Step-2
		//.next(casaTransformationStep)
		.end()
        .listener(casaTransformationJobListener)
		.build();
	}

}