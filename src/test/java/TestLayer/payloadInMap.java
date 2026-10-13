package TestLayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import configLayer.Log;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.ThreadContext;
public class payloadInMap {

    public static void main(String[] args) {

    	 ThreadContext.put("browser", "RestAssuredLogs");
        Map<Object, Object> details = new HashMap<>();

        details.put("id", 101);
        details.put("firstName", "Ajit");
        details.put("lastName", "Veer");
        details.put("salary", 25000.50);
        details.put("isActive", true);
        
        Map<String, Object> address = new HashMap<>();
        address.put("City", "Pune");
        address.put("Pin", 415311);
        
        details.put("address", address);
        details.put("Skills", Arrays.asList("Java", "Selenium", "Rest Assured"));
        details.put("Experience", Arrays.asList(5, 6, 7.8));
        

        RestAssured.baseURI = "http://localhost:9090";

        RequestSpecification httprequest = RestAssured.given();

        httprequest.header("Content-Type", "application/json");

        httprequest.body(details);

        Response response = httprequest.post("/data");

        response.prettyPrint();
        Log.info(response.prettyPrint());
        Log.assertEquals("Verifying firstname", response.jsonPath().getString("firstName"), "Ajit");
        Log.assertTrue("Verifying status code", response.getStatusLine().contains("201"));
        Log.assertTrue("Verifying Skills ", response.jsonPath().getString("Skills").contains("Selenium"));
    }
}