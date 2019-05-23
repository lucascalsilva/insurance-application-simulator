package com.camunda.consulting.insuranceapplicationsimulator.util;

public enum Category {

		  BASISPAKET("Basispaket"), STANDARDPAKET("Standard Paket"), PREMIUMPAKET("Premium Paket");
		  
		  private String displayName;

		  Category(String displayName) {
		        this.displayName = displayName;
		    }

		    public String displayName() { return displayName; }
	
}
