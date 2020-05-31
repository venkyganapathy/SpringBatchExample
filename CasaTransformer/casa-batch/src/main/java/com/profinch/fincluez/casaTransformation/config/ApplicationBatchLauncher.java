package com.profinch.fincluez.casaTransformation.config;


import com.profinch.fincluez.casaTransformation.library.TransformationJobStatus;
import com.profinch.fincluez.casaTransformation.repo.TransformationJobStatusRepo;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class ApplicationBatchLauncher implements CommandLineRunner {
//public class ApplicationBatchLauncher {

	Logger log = LoggerFactory.getLogger(ApplicationBatchLauncher.class);


	@Autowired
	@Qualifier("casaTransformationBatchProcessJob")
	private Job casaTransformationBatchProcessJob;

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private TransformationJobStatusRepo transformationJobStatusRepo;

	/*
	@Bean
	public ThreadPoolTaskExecutor taskExecutorForStep() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(executorCorePoolSize);
		taskExecutor.setMaxPoolSize(executorMaxPoolSize);
		taskExecutor.setQueueCapacity(executorQueueCapacity);
		taskExecutor.setThreadNamePrefix("FinCluez-CasaTransformer-");
		return taskExecutor;
	}
	*/

	private Date getToday() {
		Date today = new Date();
		DateUtils.truncate(today, Calendar.DATE);
		return today;
	}

	private Date getElRunDateToBeRun(){
		log.debug("AppLauncher --> Inside....getElRunDateToBeRun.....");
		Date elRunDateToBeRun = new Date();
		List<TransformationJobStatus> transformationJobStatusList =
				transformationJobStatusRepo.findTop1ByJobNameAndStepNameOrderByElRunDateDesc
						(JobName.CASA_TRANSFORMATION_JOB.toString()
								,JobName.CASA_TRANSFORMATION_JOB.toString());
		log.debug("AppLauncher --> transformationJobStatusList....{}",transformationJobStatusList.toString());
		if (transformationJobStatusList != null
			&& !transformationJobStatusList.isEmpty()
			&& transformationJobStatusList.size()>0){
			log.debug("Found the last EL Run Date as {}",transformationJobStatusList.get(0).getElRunDate());
			elRunDateToBeRun = DateUtils.addDays(transformationJobStatusList.get(0).getElRunDate(),1);
		}
		DateUtils.truncate(elRunDateToBeRun, Calendar.DATE);
		log.debug("AppLauncher --> Returning....ElRunDateToBeRun...as {}",elRunDateToBeRun);
		return elRunDateToBeRun;
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception
	{
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		//jobLauncher.setTaskExecutor(taskExecutor);
		return jobLauncher;
	}


	/*
	public void fincluezCasaTransformationBatchLauncher() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		log.debug("***** Casa Transformation Job Launcher - Launched");
		jobLauncher.run(casaTransformationBatchProcessJob, getJobParameters());
	}
	*/

	/*
	private JobParameters getJobParameters() {
		Map<String, JobParameter> parameters = new HashMap<>();
		JobParameter parameter = new JobParameter(new Date());
		parameters.put("currentTime", parameter);
		return new JobParameters(parameters);
	}
	*/


	public JobParameters getJobParametersFromCmdLine(String[] args, Date elRunDate) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		// param 0
		jobParametersBuilder.addString("entityCode", args[0]);
		// param 1
		jobParametersBuilder.addString("branchCode", args[1]);

		jobParametersBuilder.addDate("elRunDate", elRunDate);

		jobParametersBuilder.addLong("currentTime",new Date().getTime());
		return jobParametersBuilder.toJobParameters();
	}

	@Override
	public void run(String... args) throws Exception {
		log.debug("***** Casa Transformation Job Launcher - Launched");
		JobParameters jobParameters;

		Date elRunDateToBeRun = getElRunDateToBeRun();
		Date today = getToday();

		while (elRunDateToBeRun.before(today) || elRunDateToBeRun.equals(today)){
			log.debug("Launching CASA Transformation for Date => {}",elRunDateToBeRun);
			jobParameters = getJobParametersFromCmdLine(args,elRunDateToBeRun);
			jobLauncher.run(casaTransformationBatchProcessJob,jobParameters);
			elRunDateToBeRun = getElRunDateToBeRun();
		}

	}
}

