package com.camunda.consulting.insuranceapplicationsimulator.model;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewApplication {

  private String applicationNumber;
  private Person applicant;
  private String vehicleManufacturer;
  private String vehicleType;
  private String product;
  private long priceIndicationInCent;

  public String getApplicationNumber() {
    return applicationNumber;
  }

  public void setApplicationNumber(String applicationNumber) {
    this.applicationNumber = applicationNumber;
  }

  public Person getApplicant() {
    return applicant;
  }

  public void setApplicant(Person applicant) {
    this.applicant = applicant;
  }

  public String getVehicleManufacturer() {
    return vehicleManufacturer;
  }

  public void setVehicleManufacturer(String vehicleManufacturer) {
    this.vehicleManufacturer = vehicleManufacturer;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public long getPriceIndicationInCent() {
    return priceIndicationInCent;
  }

  public void setPriceIndicationInCent(long priceIndicationInCent) {
    this.priceIndicationInCent = priceIndicationInCent;
  }
}
