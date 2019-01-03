package com.camunda.consulting.insuranceapplicationsimulator;

import com.camunda.consulting.insuranceapplicationsimulator.batch.InsuranceApplicationBatch;
import com.camunda.consulting.insuranceapplicationsimulator.exception.InvalidArgumentException;
import com.camunda.consulting.insuranceapplicationsimulator.model.BatchProcess;
import com.camunda.consulting.insuranceapplicationsimulator.util.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class InsuranceApplicationSimulatorApplication implements CommandLineRunner {

	private static final Logger LOGGER = Logger.getLogger(InsuranceApplicationSimulatorApplication.class.getName());

	@Autowired
	private InsuranceApplicationBatch insuranceApplicationBatch;

	public static void main(String[] args) {
		new SpringApplicationBuilder(InsuranceApplicationSimulatorApplication.class).web(WebApplicationType.NONE).run(args);
	}

	@Override
	public void run(String... args){
		Integer numberOfApplications;
		Integer waitTime;
		Language language;
		String insuranceAppEndpoint = null;
		String baseRequestFilename = null;

		if (args.length > 0) numberOfApplications =  Integer.valueOf(args[0]);
		else throw new InvalidArgumentException("Argument number of applications must be provided.");

		if (args.length > 1) waitTime =  Integer.valueOf(args[1]);
		else throw new InvalidArgumentException("Argument wait time must be provided.");

		if (args.length > 2) language =  Language.valueOf(args[2].toLowerCase());
		else throw new InvalidArgumentException("Argument language must be provided.");

		if (args.length > 3) insuranceAppEndpoint =  args[3];

		if (args.length > 4) baseRequestFilename =  System.getProperty("user.dir") + "/" + args[4];

		try {
			insuranceApplicationBatch.process(new BatchProcess(numberOfApplications, waitTime, language,
					baseRequestFilename, insuranceAppEndpoint));
		}
		catch(Exception e){
			LOGGER.severe(e.getMessage());
		}
		System.exit(0);
	}

}

