# Insurance application simulator
Utility that creates demo data for the Insurance Application Simulator process in Camunda BPM.

## Purpose

This small utility can be used to test Camunda's Insurance Showcase. This first version is a spring boot command line application package inside a JAR.

All insurance applications are created using a template (base-request.json), which can be overwritten.

```javascript
{
  "applicant":{
    "email":"mission@camunda.com"
  },
  "vehicleManufacturer":"VW",
  "vehicleType":"Golf V",
  "product":"Camundanzia Vollkasko Plus",
  "priceIndicationInCent":15000
}
```

The applicant's name is randomly generated. For the birthday (which affects the age that will affect the risk associated with that person), if you provide it in your custom "base-request.json" file, then the application will use that for all the generated requests, otherwise, it will choose a random date between 1980 and 2000. Below is a JSON file with the birthday on it:

```javascript
{
  "applicant":{
    "email":"mission@camunda.com",
    "birthday":"2000-01-01T00:00:00"
  },
  "vehicleManufacturer":"VW",
  "vehicleType":"Golf V",
  "product":"Camundanzia Vollkasko Plus",
  "priceIndicationInCent":15000
}
```

## How-To

To execute the application, just build it and run the following command with the generated jar:

java -jar -Dnumapp=300 -Dwaittime=1 -Dlanguage=en -Dinsappendpoint=http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de -Dbasereqfile=custom-request.json -Dapplemail=lucas.silva@camunda.com insurance-application-simulator-0.0.1-SNAPSHOT.jar

Below is the description of the arguments:

* **-Dnumapp:** Number of dummy applications that need to be created.
* **-Dwaittime:** Time (in seconds) that the utiliy will wait between creating each dummy application.
* **-Dlanguage:** Language that will be used, can either be "de" (Deutsch) or "en" (English).
* **-Dinsappendpoint (Optional):** Overwrites the default endpoint for the rest api of the insurance application. The default is "http://localhost:8080/camunda-showcase-insurance-application/api/new-application".
* **-Dbasereqfile (Optional):** Overwrites the base-request.json file with a custom one (must be in the same directory that the jar is being executed).
* **-Dapplemail (Optional):** E-mail which will be used as "applicant email". The default is: "mission@camunda.com".

## Example:

**Creates 10 applications using Deutsch as language.**

java -jar -Dnumapp=10 -Dwaittime=0 -Dlanguage=de insurance-application-simulator-0.0.1-SNAPSHOT.jar

**Creates 100 applications, each one at every 3 seconds, using English as language and a custom endpoint for the insurance application api.**

java -jar -Dnumapp=100 -Dwaittime=5 -Dlanguage=en -Dinsappendpoint=http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de insurance-application-simulator-0.0.1-SNAPSHOT.jar

**Creates 200 applications, each one at every 2 seconds, using the English as language, a custom endpoint for the insurance application api and a custom-file.json as the base request file.**

java -jar -Dnumapp=200 -Dwaittime=2 -Dlanguage=en -Dinsappendpoint=http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de -Dbasereqfile=custom-request.json insurance-application-simulator-0.0.1-SNAPSHOT.jar

**Creates 300 applications, each one at every 1 seconds, using the English as language, a custom endpoint for the insurance application api, a custom-file.json as the base request file and a different applicant email.**

java -jar -Dnumapp=300 -Dwaittime=1 -Dlanguage=en -Dinsappendpoint=http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de -Dbasereqfile=custom-request.json -Dapplemail=lucas.silva@camunda.com insurance-application-simulator-0.0.1-SNAPSHOT.jar
