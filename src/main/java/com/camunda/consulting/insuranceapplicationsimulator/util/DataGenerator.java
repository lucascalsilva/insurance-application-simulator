package com.camunda.consulting.insuranceapplicationsimulator.util;

import com.camunda.consulting.insuranceapplicationsimulator.model.BatchProcess;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.camunda.consulting.insuranceapplicationsimulator.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;

@Component
public class DataGenerator {

    private String baseRequest;

    @Autowired
    private DataLoader dataLoader;

    public List<NewApplication> getRandomApplications(BatchProcess batchProcess) throws IOException {
        List<NewApplication> applications = new ArrayList<>();
        if(batchProcess.getBaseRequestFilename() != null) dataLoader.setExtBaseFileRequestJson(batchProcess.getBaseRequestFilename());
        for(int i=0;i<batchProcess.getNumberOfApplications();i++) {
            NewApplication newApplication = dataLoader.getBaseApplication();
            Sex sex = getRandomSex();
            newApplication.setCorporation(getRandomCorporation());
            newApplication.setCategory(getRandomCategory());
            newApplication.setEmployment(getRandomEmployment());
            newApplication.getApplicant().setGender(sex.name());
            newApplication.getApplicant().setName(getRandomName(sex, batchProcess.getLanguage()));
            if(batchProcess.getApplicantMail() != null) newApplication.getApplicant().setEmail(batchProcess.getApplicantMail());
            if(newApplication.getApplicant().getBirthday() == null) newApplication.getApplicant().setBirthday(Date.from(LocalDate.parse(getRandomDate()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            applications.add(newApplication);
        }
        return applications;
    }
    
    private String getRandomCorporation() {
    	return new String[] {"Camuntelia", "Camunbankia", "Camsurance"}[randBetween(0,2)];
    }

    private String getRandomEmployment() {
    	int randomNumber = randBetween(0,4);
    	return Employment.values()[randomNumber].displayName();
    }

    private String getRandomCategory() {
    	int randomNumber = randBetween(0,2);
    	return Category.values()[randomNumber].displayName();
    }

    private Sex getRandomSex(){
        int randomNumber = randBetween(0,1);
        return Sex.values()[randomNumber];
    }

    private String getRandomName(Sex sex, Language language){
        List<String> firstNames;
        List<String> surnames;

        if(sex.equals(Sex.Frau))
            firstNames = dataLoader.getFirstNamesFemale();
        else
            firstNames = dataLoader.getFirstNamesMale();

        if(language.equals(Language.de))
            surnames = dataLoader.getSurnamesDe();
        else
            surnames = dataLoader.getSurnamesEn();

        int firstNameRandomNumber = randBetween(0, firstNames.size()-1);
        int surnameRandomNumber = randBetween(0, surnames.size()-1);


        return firstNames.get(firstNameRandomNumber) + " " + surnames.get(surnameRandomNumber);
    }

    private String getRandomDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1980, 2000);
        gc.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        return sdf.format(gc.getTime());
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
