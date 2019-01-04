package com.camunda.consulting.insuranceapplicationsimulator.model;

import com.camunda.consulting.insuranceapplicationsimulator.util.Language;

public class BatchProcess {

    private Integer numberOfApplications;
    private Integer waitTime;
    private Language language;
    private String applicantMail;
    private String baseRequestFilename;
    private String insuranceAppEndpoint;

    public BatchProcess(Integer numberOfApplications, Integer waitTime, Language language, String applicantMail, String baseRequestFilename, String insuranceAppEndpoint) {
        this.numberOfApplications = numberOfApplications;
        this.waitTime = waitTime;
        this.language = language;
        this.applicantMail = applicantMail;
        this.baseRequestFilename = baseRequestFilename;
        this.insuranceAppEndpoint = insuranceAppEndpoint;
    }

    public Integer getNumberOfApplications() {
        return numberOfApplications;
    }

    public void setNumberOfApplications(Integer numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getApplicantMail() {
        return applicantMail;
    }

    public void setApplicantMail(String applicantMail) {
        this.applicantMail = applicantMail;
    }

    public String getBaseRequestFilename() {
        return baseRequestFilename;
    }

    public void setBaseRequestFilename(String baseRequestFilename) {
        this.baseRequestFilename = baseRequestFilename;
    }

    public String getInsuranceAppEndpoint() {
        return insuranceAppEndpoint;
    }

    public void setInsuranceAppEndpoint(String insuranceAppEndpoint) {
        this.insuranceAppEndpoint = insuranceAppEndpoint;
    }
}
