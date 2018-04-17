/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import coursesitegenerator.CourseSiteGeneratorApp;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Varun
 */
public class CourseInfoData implements AppDataComponent {
ObservableList<Courses> courses;
CourseSiteGeneratorApp app;
public CourseInfoData(CourseSiteGeneratorApp app){
    this.app = app;
    courses = FXCollections.observableArrayList();
}
public ObservableList getCourse(){
    return this.courses;
}
public void addCourse(String subject, String semester, String number, String year) {
      
        Courses course = new Courses(subject,semester,number,year);


        
            courses.add(course);
        

       
    }
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
