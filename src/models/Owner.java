package models;

import utils.Constants;
import utils.Utils;

public class Owner {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String fullName;

    Owner(String id, String firstName, String lastName) {
        this.id = Utils.getValidUserInput(id);
        this.firstName = Utils.getValidUserInput(firstName);
        this.lastName = Utils.getValidUserInput(lastName);
        this.fullName = this.firstName + Constants.SPACE + this.lastName;
    }
    
    
    //Getters
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }
}
