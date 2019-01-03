package com.camunda.consulting.insuranceapplicationsimulator.util;

import com.camunda.consulting.insuranceapplicationsimulator.exception.FileProcessingError;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class SimulatorHelper {

    private static final Logger LOGGER = Logger.getLogger(SimulatorHelper.class.getName());

    public String fileToString(String filename, Boolean isResourceFile){
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = getBufferedReader(filename, isResourceFile);
            line = reader.readLine();
            while(line != null){
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            throw new FileProcessingError("Error when converting the file to string "+filename);
        }
        return stringBuilder.toString();
    }

    public List<String> fileToList(String filename, Boolean isResourceFile) {
        List<String> results = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = getBufferedReader(filename, isResourceFile);
            line = reader.readLine();
            while(line != null){
                results.add(line);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            throw new FileProcessingError("Error when converting the file to list "+filename);
        }
        return results;
    }

    private BufferedReader getBufferedReader(String filename, Boolean isResourceFile) {
        try {
            Reader reader;
            if (isResourceFile) {
                reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename));
            } else {
                reader = new InputStreamReader(new FileInputStream(filename));
            }
            return new BufferedReader(reader);
        }
        catch(FileNotFoundException e){
            throw new FileProcessingError("Error when reading the file "+filename);
        }
    }
}
