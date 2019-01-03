package com.camunda.consulting.insuranceapplicationsimulator.batch;

import com.camunda.consulting.insuranceapplicationsimulator.adapter.InsuranceApplicationAdapter;
import com.camunda.consulting.insuranceapplicationsimulator.exception.InsuranceAppServiceException;
import com.camunda.consulting.insuranceapplicationsimulator.model.BatchProcess;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.camunda.consulting.insuranceapplicationsimulator.util.DataGenerator;
import com.camunda.consulting.insuranceapplicationsimulator.util.Language;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class InsuranceApplicationBatch {

    private static final Logger LOGGER = Logger.getLogger(InsuranceApplicationBatch.class.getName());

    @Autowired
    private InsuranceApplicationAdapter adapter;

    @Autowired
    private DataGenerator dataGenerator;

    public void process(BatchProcess batchProcess){

        adapter.setEndpoint(batchProcess.getInsuranceAppEndpoint());
        List<NewApplication> applications = dataGenerator.getRandomApplications(batchProcess.getNumberOfApplications(),
                batchProcess.getLanguage(), batchProcess.getBaseRequestFilename());

        for(int i=0;i<applications.size();i++){
            try {
                LOGGER.info("Starting to process record "+i);
                LOGGER.info(new ObjectMapper().writeValueAsString(applications.get(i)));
                adapter.merge(applications.get(i), batchProcess.getLanguage());
                LOGGER.info("Record processed successfully.");
                TimeUnit.SECONDS.sleep(batchProcess.getWaitTime());
            } catch (InterruptedException | JsonProcessingException | InsuranceAppServiceException e) {
                LOGGER.severe(e.getMessage());
            }
        }
     }
}
