package pojoLayer;

import java.util.List;
import lombok.Data;

@Data
public class MyDetailsPojo {

    private int id;
    private String name;
    private double salary;
    private boolean active;
    private String middleName;

    private MyAddressDetails address;

    private List<String> skills;

    private List<Double> experience;

    private List<MyProjectDetails> projects;

    private MyContactDetails contact;
}