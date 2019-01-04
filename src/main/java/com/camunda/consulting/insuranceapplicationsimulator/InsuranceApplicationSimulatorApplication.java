package com.camunda.consulting.insuranceapplicationsimulator;

import com.camunda.consulting.insuranceapplicationsimulator.batch.InsuranceApplicationBatch;
import com.camunda.consulting.insuranceapplicationsimulator.model.BatchProcess;
import com.camunda.consulting.insuranceapplicationsimulator.util.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InsuranceApplicationSimulatorApplication implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(InsuranceApplicationSimulatorApplication.class);

	@Autowired
	private InsuranceApplicationBatch insuranceApplicationBatch;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(InsuranceApplicationSimulatorApplication.class).web(WebApplicationType.NONE).run(args);
		ctx.close();
	}

	@Override
	public void run(String... args){
		try {
			insuranceApplicationBatch.process(validateArgsAndCreateBatchProcess(args));
		}
		catch(Exception e){
			logger.error("An error occurred during the execution of the Insurance Application Simulator", e);
		}
	}

	private BatchProcess validateArgsAndCreateBatchProcess(String... args){
		Integer numberOfApplications = Integer.valueOf(System.getProperty("numapp"));
		Integer waitTime = Integer.valueOf(System.getProperty("waittime"));
		Language language = Language.valueOf(System.getProperty("language"));
		String applicantMail = System.getProperty("applemail");
		String insuranceAppEndpoint = System.getProperty("insappendpoint");
		String baseRequestFilename = System.getProperty("basereqfile");

		if (numberOfApplications == null) throw new IllegalArgumentException("Argument number of applications must be provided.");

		if (waitTime == null) throw new IllegalArgumentException("Argument wait time must be provided.");

		if (language == null) throw new IllegalArgumentException("Argument wait time must be provided.");

		return new BatchProcess(numberOfApplications, waitTime, language, applicantMail,
				baseRequestFilename, insuranceAppEndpoint);
	}
}

