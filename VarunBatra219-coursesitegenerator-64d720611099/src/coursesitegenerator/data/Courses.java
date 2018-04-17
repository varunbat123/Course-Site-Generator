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
public class Courses {
        // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty subject;
    private final StringProperty semester;
    private final StringProperty number;
    private final StringProperty year;
    /**
     * Constructor initializes the TA name
     */
 public Courses(String subject, String semester,String number, String year ){
     
     this.subject = new SimpleStringProperty(subject);
     this.semester = new SimpleStringProperty(semester);
     this.number = new SimpleStringProperty(number);
     this.year = new SimpleStringProperty(year);
     
 }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getSubject() {
        return subject.get();
    }
    public String getSemester() {
        return semester.get();
    }
    public String getNumber() {
        return number.get();
    }
    public String getYear() {
        return year.get();
    }

    public void setSubject(String sub) {
        subject.set(sub);
    }
    public void setSemester(String sem) {
        semester.set(sem);
    }
    public void setNumber(String num) {
        number.set(num);
    }
    public void setYear(String yr) {
        year.set(yr);
    }

    
    
    @Override
    public String toString() {
        return subject.getValue();
    }
}

