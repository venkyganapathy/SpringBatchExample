package com.profinch.fincluez.casaTransformation.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationBatchLauncher {

	Logger log = LoggerFactory.getLogger(ApplicationBatchLauncher.class);

	//private final int executorCorePoolSize = 1; // Number of Jobs
	//private final int executorMaxPoolSize = 5; // Max Number of Threads for Jobs
	//private final int executorQueueCapacity = 10; // Max Jobs in Queue

	@Autowired
	@Qualifier("casaTransformationBatchProcessJob")
	private Job casaTransformationBatchProcessJob;

	@Autowired
	private JobLauncher jobLauncher;

/*	@Bean
	public ThreadPoolTaskExecutor taskExecutorForStep() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(executorCorePoolSize);
		taskExecutor.setMaxPoolSize(executorMaxPoolSize);
		taskExecutor.setQueueCapacity(executorQueueCapacity);
		taskExecutor.setThreadNamePrefix("FinCluez-CasaTransformer-");
		return taskExecutor;
	}*/

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception
	{
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		//jobLauncher.setTaskExecutor(taskExecutor);
		return jobLauncher;
	}


	public void fincluezCasaTransformationBatchLauncher() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		log.debug("***** Casa Transformation Job Launcher - Launched");
		jobLauncher.run(casaTransformationBatchProcessJob, newExecution());
	}

	private JobParameters newExecution() {
		Map<String, JobParameter> parameters = new HashMap<>();
		JobParameter parameter = new JobParameter(new Date());
		parameters.put("currentTime", parameter);
		return new JobParameters(parameters);
	}
}

