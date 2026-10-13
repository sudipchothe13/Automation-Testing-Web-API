package TestLayer;

import java.sql.SQLException;
import java.util.Map;

import org.testng.annotations.Test;

import configLayer.Context;
import configLayer.Log;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.RootRequest;
import pojo.RootResponse;
import testDataLayer.PersonalDetailsResources;
import utilsLayer.APIUtils;
import utilsLayer.DBUtils;

public class RestAssuredPost_Get_Put {

	Context context = new Context();
	int psid;

	@Test
	public void postCall() throws SQLException {

		// 1. Set Base URI
		RestAssured.baseURI = "http://localhost:9090";

		// 2. Create Request Specification
		RequestSpecification httpRequest = RestAssured.given();

		// 3. Set Headers
		httpRequest.header("Content-Type", "application/json");

		// 4. Create Request POJO
		PersonalDetailsResources p = new PersonalDetailsResources();
		RootRequest request = p.personalDetails();
		context.setRootRequest(request);
		
		
		// 5. Serialize Request POJO -> JSON
		String payload = APIUtils.serialization(request);

		httpRequest.body(payload);

		// 6. Send POST Request
		Response response = httpRequest.post("/personal-details");

		// 7. Print Response
		response.prettyPrint();

		// 8. Validate HTTP Status Code
		Log.assertEquals("Verifying status code", response.getStatusCode(), 201);
		Log.assertTrue("Verifying status line contains Created", (response.getStatusLine()).contains("Created"));

		// 9. Validate JSON Schema
		APIUtils.jsonSchemaValidator(response, "JsonSchemaFiles/PersonalDetailsSchema.json");

		// 10. Deserialize Response JSON -> Response POJO
		RootResponse rootResponse = response.as(RootResponse.class);

		// 11. Store Response in Context
		context.setRootResponse(rootResponse);

		// 12. Request vs Response Assertions
		Log.assertEquals("Verifying first name Request vs Response",
				request.getData().getPersonalDetails().getFirstName(),
				rootResponse.getData().getPersonalDetails().getFirstName());
		/*
		 * Business logic has been intentionally changed for the currentCTC field to
		 * verify Request vs Response validation.
		 *
		 * Request payload: currentCTC = 2000 API transformation: currentCTC = 2000 /
		 * 1000 = 2
		 *
		 * Therefore, the below Request vs Response assertion is expected to fail
		 * because the API intentionally transforms the value before returning it.
		 * 
		 * 
		 * Log.assertEquals("Verifying current CTC Request vs Response",
		 * request.getData().getPersonalDetails().getCurrentCTC(),
		 * rootResponse.getData().getPersonalDetails().getCurrentCTC());
		 */
		// 13. Extract Generated psId
		psid = rootResponse.getData().getPersonalDetails().getPsId();

		// 14. Connect to Database
		DBUtils.connectToDB();

		// 15. Execute Database Query
		String query = """
				SELECT *
				FROM dbo.personal_details p
				INNER JOIN dbo.address a
				    ON p.ps_id = a.ps_id
				WHERE p.ps_id =
				""" + psid;

		Map<String, String> dbRecord = DBUtils.selectQuery_Map(query);

		// 16. Response vs Database Assertions
		Log.assertEquals("Verifying first name Response vs DB",
				rootResponse.getData().getPersonalDetails().getFirstName(), dbRecord.get("first_name"));

		Log.info("Response data successfully validated against database");
	}

	public void getCall() {
		
		context.getRootResponse().getData().getPersonalDetails().getFirstName();
		context.getRootRequest().getData().getPersonalDetails().getFirstName();
	}
	public void postCallShort() throws SQLException {
		
		RestAssured.baseURI = "http://localhost:9090";
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");

		// 4. Create Request POJO
		PersonalDetailsResources p = new PersonalDetailsResources();
		RootRequest request = p.personalDetails();

		// 5. Serialize Request POJO -> JSON
		String payload = APIUtils.serialization(request);
		httpRequest.body(payload);

		Response response = httpRequest.post("/personal-details");

		response.prettyPrint();

		Log.assertEquals("Verifying status code", response.getStatusCode(), 201);

		// 9. Validate JSON Schema
		APIUtils.jsonSchemaValidator(response, "JsonSchemaFiles/PersonalDetailsSchema.json");

		// 10. Deserialize Response JSON -> Response POJO
		RootResponse rootResponse = response.as(RootResponse.class);

		// 11. Store Response in Context
		context.setRootResponse(rootResponse);

		// 12. Request vs Response Assertions
		Log.assertEquals("Verifying first name Request vs Response",
				request.getData().getPersonalDetails().getFirstName(),
				rootResponse.getData().getPersonalDetails().getFirstName());

		// 13. Extract Generated psId
		psid = rootResponse.getData().getPersonalDetails().getPsId();

		// 14. Connect to Database
		DBUtils.connectToDB();

		// 15. Execute Database Query
		String query = """
				SELECT *
				FROM dbo.personal_details p
				INNER JOIN dbo.address a
				    ON p.ps_id = a.ps_id
				WHERE p.ps_id =
				""" + psid;

		Map<String, String> dbRecord = DBUtils.selectQuery_Map(query);

		// 16. Response vs Database Assertions
		Log.assertEquals("Verifying first name Response vs DB",
				rootResponse.getData().getPersonalDetails().getFirstName(), dbRecord.get("first_name"));

	}
}