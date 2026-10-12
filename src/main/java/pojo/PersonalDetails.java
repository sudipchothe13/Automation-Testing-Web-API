package pojo;

import java.util.List;

import lombok.Data;

@Data
public class PersonalDetails {
	
	private Integer psId;
	private String 	firstName;
	private String lastName;
	private double totalExperience;
	private String currentCompany;
	private double currentCTC;
	private Boolean isReadyToRelocate;
	private List<String> primarySkills;
	private Address address;
	

}
