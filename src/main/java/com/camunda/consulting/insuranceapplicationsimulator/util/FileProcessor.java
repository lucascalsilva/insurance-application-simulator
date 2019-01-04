package com.camunda.consulting.insuranceapplicationsimulator.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FileProcessor {

    public String fileToString(String filename, Boolean isResourceFile) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader reader = null;
        try {
            reader = getBufferedReader(filename, isResourceFile);
            line = reader.readLine();
            while(line != null){
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            closeBufferedReader(reader);
        }
        return stringBuilder.toString();
    }

    public List<String> fileToList(String filename, Boolean isResourceFile) throws IOException {
        List<String> results = new ArrayList<>();
        String line;
        BufferedReader reader = null;
        try {
            reader = getBufferedReader(filename, isResourceFile);
            line = reader.readLine();
            while(line != null){
                results.add(line);
                line = reader.readLine();
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            closeBufferedReader(reader);
        }
        return results;
    }

    private BufferedReader getBufferedReader(String filename, Boolean isResourceFile) throws IOException {
        Reader reader;
        if (isResourceFile) {
            reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)));
        } else {
            reader = new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "/" + filename));
        }
        return new BufferedReader(reader);

    }

    private void closeBufferedReader(BufferedReader reader) throws IOException {
        if (reader != null)
            reader.close();
    }
}
