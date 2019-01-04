package com.camunda.consulting.insuranceapplicationsimulator.batch;

import com.camunda.consulting.insuranceapplicationsimulator.adapter.InsuranceApplicationAdapter;
import com.camunda.consulting.insuranceapplicationsimulator.exception.BatchProcessingException;
import com.camunda.consulting.insuranceapplicationsimulator.exception.InsuranceAppServiceException;
import com.camunda.consulting.insuranceapplicationsimulator.model.BatchProcess;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.camunda.consulting.insuranceapplicationsimulator.util.DataGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class InsuranceApplicationBatch {

    private static final Logger logger = LogManager.getLogger(InsuranceApplicationBatch.class);

    @Autowired
    private InsuranceApplicationAdapter adapter;

    @Autowired
    private DataGenerator dataGenerator;

    public void process(BatchProcess batchProcess) throws BatchProcessingException {

        try {
            adapter.setEndpoint(batchProcess.getInsuranceAppEndpoint());
            List<NewApplication> applications = dataGenerator.getRandomApplications(batchProcess);

            int count = 0;
            int successCount = 0;

            for (NewApplication application : applications) {
                try {
                    count++;
                    logger.info("Starting to process record " + count);
                    logger.info(new ObjectMapper().writeValueAsString(application));
                    adapter.create(application, batchProcess.getLanguage());
                    logger.info("Record " +count+ " processed successfully. Application number:"+application.getApplicationNumber());
                    successCount++;
                    TimeUnit.SECONDS.sleep(batchProcess.getWaitTime());
                } catch (InterruptedException | JsonProcessingException | InsuranceAppServiceException e) {
                    logger.error("Error when creating the application " + count, e);
                }
            }
            logger.info("Total records created: "+successCount);
            logger.info("Total records with error: "+(count - successCount));
        }
        catch(Exception e){
            throw new BatchProcessingException("Error when processing the batch to create the applications", e);
        }
    }
}
