package stepDefinition;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import TestLayer.RestAssuredPost_Get_Put;
import baseLayer.BaseClass;
import configLayer.Context;
import configLayer.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.RootResponse;
import testDataLayer.PersonalDetailsResources;
import utilsLayer.DBUtils;
import utilsLayer.APIUtils;

public class personalDetailsStepDef {
	Context context;

	@Given("initialize request for Personal Details POST API")
	public void initialize_request_for_personal_details_post_api() {

		BaseClass.initializeApiRequest();

//		Map<String, String> headers = new HashMap<>();
//		headers.put("Content-Type", "application/json");
//		BaseClass.setHeaders(headers);
	}

	@When("user sends Personal Details POST request")
	public void user_sends_personal_details_post_request() throws SQLException {
		
		RestAssuredPost_Get_Put b = new RestAssuredPost_Get_Put();
		b.postCall();

//	    PersonalDetailsResources resources = new PersonalDetailsResources();
//	    String payload = APIUtils.serialization(resources.personalDetails());
//	    BaseClass.setRequestBody(payload);
//
//	    BaseClass.setPathParams("/personal-details");
//	    Response response = BaseClass.setRequestMethod("POST");
//
//	    System.out.println("POST Response:");
//	    System.out.println(response.asPrettyString());
//	    
//	    
//	    
//	 // Verify response structure
//	    APIUtils.jsonSchemaValidator(
//	            response,
//	            "JsonSchema/PersonalDetailsSchema.json"
//	    );
//	    Log.info("Json Schema validated successfully");
//
//	    context = new Context();
//
//	    RootResponse rootResponse = response.as(RootResponse.class);
//	    context.setRootResponse(rootResponse);

	}

	@Then("verify Personal Details API response with assertions")
	public void verify_personal_details_api_response_with_assertions() throws SQLException {

//		Integer psid = context.getRootResponse().getData().getPersonalDetails().getPsId();
//		Log.assertEquals("Verifying Firstname ",
//				context.getRootResponse().getData().getPersonalDetails().getFirstName(), "Sudip");
//		
//		Log.assertEquals("Verifying skill", context.getRootResponse().getData().getPersonalDetails().getPrimarySkills().get(1), "Java");
//		Log.assertTrue("Verifying skill", context.getRootResponse().getData().getPersonalDetails().getPrimarySkills().contains("Selenium"));
//		Log.assertEquals("Verifying citi", context.getRootResponse().getData().getPersonalDetails().getAddress().getCity(), "Pune");
//		
//		DBUtils.connectToDB();
//
//		String query =  """
//							SELECT * FROM personal_details_db.dbo.personal_details 
//				    		INNER JOIN personal_details_db.dbo.address 
//				    		ON personal_details_db.dbo.personal_details.ps_id = personal_details_db.dbo.address.ps_id 
//				    		WHERE personal_details_db.dbo.personal_details.ps_id = 
//				
//						""" + psid;
//		
//		Map<String, String> personalDetailsDBRecord = DBUtils.selectQuery_Map(query);	
//		Log.assertEquals("Verifying username API to DB", context.getRootResponse().getData().getPersonalDetails().getFirstName(), personalDetailsDBRecord.get("first_name"));
//		
//		
		
	}

}
