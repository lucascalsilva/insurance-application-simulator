package com.camunda.consulting.insuranceapplicationsimulator.util;

public enum Employment {
	  NICHT_ERWERBSTAETIG("Nicht erwerbstätig"), FREELANCER("Freelancer"), SELBSTSTAENDIG("Selbstständig"), FEST_ANGESTELLT("Fest angestellt"), TEILZEIT("Teilzeit");
	  
	  private String displayName;

	  Employment(String displayName) {
	        this.displayName = displayName;
	    }

	    public String displayName() { return displayName; }
}

