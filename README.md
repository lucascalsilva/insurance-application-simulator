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

The applicant's name and birthday are randomly generated.

## How-To

To execute the application, just build it and run the following command with the generated jar:

java -jar insurance-application-simulator-0.0.1-SNAPSHOT.jar [number of applications] [wait time] [language] [base request filename] [insurance application endpoint]

Below is the description of the arguments:

* **[number of applications]:** Number of dummy applications that need to be created.
* **[wait time]:** Time (in seconds) that the utiliy will wait between creating each dummy application.
* **[language]:** Language that will be used, can either be "de" (Deutsch) or "en" (English).
* **[insurance application endpoint] (Optional):** Overwrites the default endpoint for the rest api of the insurance application. The default is "http://localhost:8080/camunda-showcase-insurance-application/api/new-application".
* **[base request filename] (Optional):** Overwrites the base-request.json file with a custom one (must be in the same directory that the jar is being executed).

## Example:

**Creates 10 applications using Deutsch as language.**

java -jar insurance-application-simulator-0.0.1-SNAPSHOT.jar 10 0 de

**Creates 100 applications, each one at every 3 seconds, using English as language and a custom endpoint for the insurance application api.**

java -jar insurance-application-simulator-0.0.1-SNAPSHOT.jar 100 3 en http://localhost:9090/camunda-showcase-insurance-application/api/new-application

**Creates 200 applications, each one at every 2 seconds, using the English as language, a custom endpoint for the insurance application api and a custom-file.json as the base request file.**

java -jar insurance-application-simulator-0.0.1-SNAPSHOT.jar 200 2 en http://localhost:9090/camunda-showcase-insurance-application/api/new-application custom-file.json
