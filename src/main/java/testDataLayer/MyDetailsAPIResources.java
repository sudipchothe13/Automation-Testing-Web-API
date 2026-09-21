package testDataLayer;

import java.util.Arrays;

import pojoLayer.MyAddressDetails;
import pojoLayer.MyContactDetails;
import pojoLayer.MyDetailsPojo;
import pojoLayer.MyProjectDetails;

public class MyDetailsAPIResources {

    public MyDetailsPojo myDetailsAPI() {

        MyDetailsPojo myDetailsPojo = new MyDetailsPojo();

        myDetailsPojo.setId(101);
        myDetailsPojo.setName("Sudip");
        myDetailsPojo.setSalary(25000.50);
        myDetailsPojo.setActive(true);
        myDetailsPojo.setMiddleName(null);

        // Address
        MyAddressDetails address = new MyAddressDetails();
        address.setCity("Pune");
        address.setPincode(411001);

        myDetailsPojo.setAddress(address);

        // Skills
        myDetailsPojo.setSkills(
                Arrays.asList("Java", "Selenium", "Rest Assured")
        );

        // Experience
        myDetailsPojo.setExperience(
                Arrays.asList(5.0, 6.0, 7.8)
        );

        // Project 1
        MyProjectDetails project1 = new MyProjectDetails();
        project1.setId(1);
        project1.setName("HSBC");
        project1.setActive(true);

        // Project 2
        MyProjectDetails project2 = new MyProjectDetails();
        project2.setId(2);
        project2.setName("Billing");
        project2.setActive(false);

        myDetailsPojo.setProjects(
                Arrays.asList(project1, project2)
        );

        // Contact
        MyContactDetails contact = new MyContactDetails();
        contact.setEmail("test@gmail.com");
        contact.setPhone("9876543210");

        myDetailsPojo.setContact(contact);

        return myDetailsPojo;
    }
}