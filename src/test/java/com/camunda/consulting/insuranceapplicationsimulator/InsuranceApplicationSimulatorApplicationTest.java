package com.camunda.consulting.insuranceapplicationsimulator;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.eclipse.jetty.util.IO;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InsuranceApplicationSimulatorApplicationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void testDefaultSuccess() throws IOException {
        String numberOfApps = "1";
        initTest(numberOfApps, "0", "de", null, null, null);

        ByteArrayOutputStream os = redirectOutput();
        InsuranceApplicationSimulatorApplication.main(new String[]{""});

        String osText = os.toString();
        assertEquals(osText.contains("Total records created: "+numberOfApps), true);
        os.close();
    }

    @Test
    public void testWithOtherEndpointSuccess() throws IOException {
        String numberOfApps = "1";
        initTest(numberOfApps, "0", "de", null,
                "http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de", null);

        ByteArrayOutputStream os = redirectOutput();
        InsuranceApplicationSimulatorApplication.main(new String[]{""});

        String osText = os.toString();
        assertEquals(osText.contains("Total records created: "+numberOfApps), true);
        os.close();
    }

    @Test
    public void testWithOtherEmailSuccess() throws IOException {
        String numberOfApps = "1";
        String applicantEmail = "mission2@camunda.com";
        initTest(numberOfApps, "0", "de", applicantEmail,
                "http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de", null);

        ByteArrayOutputStream os = redirectOutput();
        InsuranceApplicationSimulatorApplication.main(new String[]{""});

        String osText = os.toString();
        assertEquals(osText.contains("Total records created: "+numberOfApps), true);
        assertEquals(StringUtils.countOccurrencesOf(osText, applicantEmail) == Integer.valueOf(numberOfApps), true);
        os.close();
    }

    @Test
    public void testWithOtherEndpointFail() throws IOException {
        String numberOfApps = "1";
        initTest(numberOfApps, "0","de",null,
                "http://localhost:8081/camunda-showcase-insurance-application/api/new-application/de", null);

        ByteArrayOutputStream os = redirectOutput();
        InsuranceApplicationSimulatorApplication.main(new String[]{""});

        String osText = os.toString();
        assertEquals(osText.contains("Total records created: 0"), true);
        os.close();
    }

    private void initTest(String numberOfApps, String waitTime, String language,
                          String applicantEmail, String insuranceAppEndpoint, String baseRequestFilename){

        System.clearProperty("numapp");
        System.clearProperty("waittime");
        System.clearProperty("language");
        System.clearProperty("applemail");
        System.clearProperty("insappendpoint");
        System.clearProperty("basereqfile");

        stubFor(post(urlEqualTo("/camunda-showcase-insurance-application/api/new-application/de"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withHeader("Referer", equalTo("http://localhost:8080/camunda-showcase-insurance-application/api/new-application/de"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("A-123")));

        if(numberOfApps!= null) System.setProperty("numapp", numberOfApps);
        if(waitTime!= null) System.setProperty("waittime", waitTime);
        if(language!= null) System.setProperty("language", language);
        if(applicantEmail!= null) System.setProperty("applemail", applicantEmail);
        if(insuranceAppEndpoint!= null) System.setProperty("insappendpoint", insuranceAppEndpoint);
        if(baseRequestFilename!= null) System.setProperty("basereqfile", baseRequestFilename);
    }

    private ByteArrayOutputStream redirectOutput(){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        return os;
    }
}
