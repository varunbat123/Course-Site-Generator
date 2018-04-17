/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Varun
 */
public class Students {
   private final StringProperty firstName;
    private final StringProperty lastName;
private StringProperty team;
private final StringProperty role;

public Students(String firstName, String lastName, String team, String role){
    this.firstName = new SimpleStringProperty(firstName);
     this.lastName = new SimpleStringProperty(lastName);
     this.role = new SimpleStringProperty(role);
     this.team = new SimpleStringProperty(team);
     
    
}
 public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getRole() {
        return role.get();
    }
    public String getTeam() {
        return team.get();
    }
    

    public void setFirstname(String first) {
        firstName.set(first);
    }
    public void setLastName(String last) {
        lastName.set(last);
    }
    public void setRole(String r) {
        role.set(r);
    }
    public void setTema(String t) {
       team.set(t);
    }

    
    
    @Override
    public String toString() {
        return firstName.getValue();
    }
 
}
