/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.json.JsonValue;

/**
 *
 * @author Varun
 */
public class Recitations {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty date;
    private final StringProperty location;
    private final StringProperty supervisor1;
    private final StringProperty supervisor2;
    
    
     public Recitations(String section, String instructor,String date, String location, String supervisor1, String supervisor2 ){
     
     this.section = new SimpleStringProperty(section);
     this.instructor= new SimpleStringProperty(instructor);
       this.date= new SimpleStringProperty(date);
     this.location = new SimpleStringProperty(location);
     this.supervisor1 = new SimpleStringProperty(supervisor1);
     this.supervisor2 = new SimpleStringProperty(supervisor2);
     
 }
     
     
     
     // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getSection() {
        return section.get();
    }
    public String getInstructor() {
        return instructor.get();
    }
    public String getLocation() {
        return location.get();
    }
    public String getSupervisor1() {
        return supervisor1.get();
    }

    public String getSupervisor2() {
       return supervisor2.get();
    }
    public void setSection(String sem) {
        section.set(sem);
    }
    public void setInstructor(String instruct) {
        instructor.set(instruct);
    }
    public void setLocation(String loc) {
        location.set(loc);
    }
     public void setSupervisor1(String x) {
        supervisor1.set(x);
    } 
     public void setSupervisor2(String x) {
        supervisor2.set(x);
    }
     

    
    
    @Override
    public String toString() {
        return section.getValue();
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String text) {
      this.date.set(text);
    }
}
    
    
    
    

