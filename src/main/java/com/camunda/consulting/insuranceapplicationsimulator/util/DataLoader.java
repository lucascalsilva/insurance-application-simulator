package com.camunda.consulting.insuranceapplicationsimulator.util;

import com.camunda.consulting.insuranceapplicationsimulator.exception.FileProcessingError;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DataLoader {

    private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());
    private String baseFileRequestJson;
    private List<String> firstNamesFemale;
    private List<String> firstNamesMale;
    private List<String> surnamesDe;
    private List<String> surnamesEn;
    private SimulatorHelper simulatorHelper;

    public DataLoader(@Value(PropertyNames.FILE_BASEREQUEST) String FILE_BASEREQUEST,
                      @Value(PropertyNames.FILE_FIRSTNAMESFEMALES) String FILE_FIRSTNAMESFEMALE,
                      @Value(PropertyNames.FILE_FIRSTNAMESMALES) String FILE_FIRSTNAMESMALES,
                      @Value(PropertyNames.FILE_SURNAMEDE) String FILE_SURNAMEDE,
                      @Value(PropertyNames.FILE_SURNAMEEN) String FILE_SURNAMEEN,
                      @Autowired SimulatorHelper simulatorHelper){

        this.simulatorHelper = simulatorHelper;
        baseFileRequestJson = simulatorHelper.fileToString(FILE_BASEREQUEST, true);
        firstNamesFemale = simulatorHelper.fileToList(FILE_FIRSTNAMESFEMALE, true);
        firstNamesMale = simulatorHelper.fileToList(FILE_FIRSTNAMESMALES, true);
        surnamesDe = simulatorHelper.fileToList(FILE_SURNAMEDE, true);
        surnamesEn = simulatorHelper.fileToList(FILE_SURNAMEEN, true);

    }

    public NewApplication getBaseApplication() {
        try {
            return new ObjectMapper().readValue(baseFileRequestJson, NewApplication.class);
        }
        catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public String getBaseFileRequestJson() {
        return baseFileRequestJson;
    }

    public void setBaseFileRequestJson(String baseFileRequestJson) {
        this.baseFileRequestJson = baseFileRequestJson;
    }

    public void setExtBaseFileRequestJson(String extBaseFileRequestJson) {
        this.baseFileRequestJson = simulatorHelper.fileToString(extBaseFileRequestJson, false);
    }

    public List<String> getFirstNamesFemale() {
        return firstNamesFemale;
    }

    public void setFirstNamesFemale(List<String> firstNamesFemale) {
        this.firstNamesFemale = firstNamesFemale;
    }

    public List<String> getFirstNamesMale() {
        return firstNamesMale;
    }

    public void setFirstNamesMale(List<String> firstNamesMale) {
        this.firstNamesMale = firstNamesMale;
    }

    public List<String> getSurnamesDe() {
        return surnamesDe;
    }

    public void setSurnamesDe(List<String> surnamesDe) {
        this.surnamesDe = surnamesDe;
    }

    public List<String> getSurnamesEn() {
        return surnamesEn;
    }

    public void setSurnamesEn(List<String> surnamesEn) {
        this.surnamesEn = surnamesEn;
    }
}
