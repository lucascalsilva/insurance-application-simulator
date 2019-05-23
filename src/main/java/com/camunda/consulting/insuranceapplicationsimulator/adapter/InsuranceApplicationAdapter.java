package com.camunda.consulting.insuranceapplicationsimulator.adapter;

import com.camunda.consulting.insuranceapplicationsimulator.exception.InsuranceAppServiceException;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.camunda.consulting.insuranceapplicationsimulator.util.Language;
import com.camunda.consulting.insuranceapplicationsimulator.util.PropertyNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
public class InsuranceApplicationAdapter {

    @Value(PropertyNames.ADAPTER_INSURANCEAPPLICATION_ENDPOINT)
    private String ADAPTER_INSURANCEAPPLICATION_ENDPOINT;
    @Value(PropertyNames.ADAPTER_INSURANCEAPPLICATION_MERGE_PATH)
    private String ADAPTER_INSURANCEAPPLICATION_MERGE_PATH;
    private String endpoint;

    public void create(NewApplication newApplication, Language language) throws InsuranceAppServiceException {
        Response response = null;
        try {
            if (endpoint == null)
                endpoint = ADAPTER_INSURANCEAPPLICATION_ENDPOINT + "/" + ADAPTER_INSURANCEAPPLICATION_MERGE_PATH + "/" + language;

            response = ClientBuilder.newClient().target(endpoint)
                    .request().header("Content-Type", "application/json")
                    .header("Referer", endpoint)
                    .post(Entity.entity(newApplication, MediaType.APPLICATION_JSON_TYPE));

            int status = response.getStatus();

            if (status != 200) {
            	System.out.println(endpoint);
                throw new InsuranceAppServiceException("Error when creating the insurance application. HTTP Status: " + status);
            }

            String applicationNumber = response.readEntity(String.class);
            newApplication.setApplicationNumber(applicationNumber);
        }
        catch(ProcessingException e){
            throw new InsuranceAppServiceException("Error when creating the insurance application.", e);
        }
        finally {
            if(response != null)
                response.close();
        }
    }

    public void setEndpoint(String endpoint){
        this.endpoint = endpoint;
    }
}