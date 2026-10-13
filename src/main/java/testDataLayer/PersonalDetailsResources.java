package testDataLayer;

import java.util.Arrays;

import pojo.Address;
import pojo.DataRequest;
import pojo.DataResponse;
import pojo.PersonalDetails;
import pojo.RootRequest;
import pojo.RootResponse;

public class PersonalDetailsResources {
	
	 public RootRequest personalDetails() {

	        Address address = new Address();
	        address.setPin(415311);
	        address.setCity("Pune");

	        PersonalDetails details = new PersonalDetails();
	        details.setFirstName("Akhay");
	        details.setLastName("Dodke");
	        details.setTotalExperience(6.2);
	        details.setCurrentCompany("LTM");
	        details.setCurrentCTC(2000);
	        details.setIsReadyToRelocate(true);
	        details.setPrimarySkills(Arrays.asList(
	            "Selenium", "Java", "BDD", "Cucumber",
	            "TestNG", "Rest Assured", "SQL"
	        ));
	        details.setAddress(address);

	        DataRequest dataRequest = new DataRequest();
	        dataRequest.setPersonalDetails(details);

	        RootRequest rootRequest = new RootRequest();
	        rootRequest.setData(dataRequest);

	        return rootRequest;
	    }

}
