package com.camunda.consulting.insuranceapplicationsimulator.adapter;

import com.camunda.consulting.insuranceapplicationsimulator.exception.InsuranceAppServiceException;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.camunda.consulting.insuranceapplicationsimulator.util.Language;
import com.camunda.consulting.insuranceapplicationsimulator.util.PropertyNames;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.webkit.BackForwardList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Component
public class InsuranceApplicationAdapter {

    private static final Logger LOGGER = Logger.getLogger(InsuranceApplicationAdapter.class.getName());

    @Value(PropertyNames.ADAPTER_INSURANCEAPPLICATION_ENDPOINT)
    private String ADAPTER_INSURANCEAPPLICATION_ENDPOINT;
    @Value(PropertyNames.ADAPTER_INSURANCEAPPLICATION_MERGE_PATH)
    private String ADAPTER_INSURANCEAPPLICATION_MERGE_PATH;
    private String endpoint;

    public void merge(NewApplication newApplication, Language language){
        if(endpoint == null)
            endpoint = ADAPTER_INSURANCEAPPLICATION_ENDPOINT + "/" + ADAPTER_INSURANCEAPPLICATION_MERGE_PATH + "/" + language;

        Invocation.Builder builder = ClientBuilder.newClient().target(endpoint)
                .request();
        Response response = builder.header("Content-Type", "application/json")
                .header("Referer", endpoint)
                .post(Entity.entity(newApplication, MediaType.APPLICATION_JSON_TYPE));

        int status = response.getStatus();

        if(status != 200) {
            response.close();
            throw new InsuranceAppServiceException("Error when creating the insurance application. HTTP Status: " + status);
        }
    }

    public void setEndpoint(String endpoint){
        this.endpoint = endpoint;
    }
}