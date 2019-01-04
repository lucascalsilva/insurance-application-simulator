package com.camunda.consulting.insuranceapplicationsimulator.util;

import com.camunda.consulting.insuranceapplicationsimulator.model.BatchProcess;
import com.camunda.consulting.insuranceapplicationsimulator.model.NewApplication;
import com.camunda.consulting.insuranceapplicationsimulator.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
            newApplication.getApplicant().setSex(sex.name());
            newApplication.getApplicant().setName(getRandomName(sex, batchProcess.getLanguage()));
            if(batchProcess.getApplicantMail() != null) newApplication.getApplicant().setEmail(batchProcess.getApplicantMail());
            newApplication.getApplicant().setBirthday(getRandomDate());
            applications.add(newApplication);
        }
        return applications;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1980, 2000);
        gc.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);

        return sdf.format(gc.getTime());
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
