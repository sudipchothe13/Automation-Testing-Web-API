package stepDefinition;

import java.util.HashMap;
import java.util.Map;

import baseLayer.BaseClass;
import configLayer.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import testDataLayer.MyDetailsAPIResources;

public class MyDetailsAPIStepDefinition extends BaseClass{

    MyDetailsAPIResources resource;
    Response response;

    @Given("user complete pre-condition")
    public void user_complete_pre_condition() {

        resource = new MyDetailsAPIResources();

        Log.info("Resource class object created");
    }

    @When("user hit Post request")
    public void user_hit_post_request() {

        BaseClass.initRestAssured();

        Log.info("Rest assured initialized");

        Map<String, String> header = new HashMap<>();

        header.put("Content-Type", "application/json");

        BaseClass.addHeaders(header);

        Log.info("Headers added");

        BaseClass.addRequestBody(resource.myDetailsAPI());

        Log.info("Request payload added");

        BaseClass.addPathParams("/MyDetails");

        Log.info("Path parameter added");

        response = BaseClass.sendRequest("POST");

        Log.info("Post request hit");

        response.prettyPrint();
    }

    @Then("user verify MyDetails API details")
    public void user_verify_my_details_api_details() {

        Log.info("Then---->");

        response.then().statusCode(200);
    }
}