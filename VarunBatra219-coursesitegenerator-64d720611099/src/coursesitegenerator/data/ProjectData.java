/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 *
 * @author Varun
 */
public class ProjectData implements AppDataComponent{
ObservableList<Team> teams;
ObservableList<Students> students;
public ProjectData(){
      teams = FXCollections.observableArrayList();  
      students = FXCollections.observableArrayList(); 
   } 
   
   public ObservableList getTeams(){
    return this.teams;
}
   public ObservableList getStudents(){
    return this.students;
}
    
   
   public void addTeam(String name, String color, String textColor, String link) {
      
        Team team = new Team(name,color,textColor,link);


        if(!containsTeam(name)){
            teams.add(team);
        
        }
       
    }
    public boolean containsTeam(String test) {
        for (Team t: teams) {
            if (t.getName().equals(test)) {
                return true;
            }
        }
        return false;
    }
     public boolean containsStudent(String firstName, String lastName) {
        for (Students s: students) {
            if ((s.getFirstName().equals(firstName))&&(s.getLastName().equals(lastName))) {
                return true;
            }
        }
        return false;
    }
    public void addStudent(String firstName, String lastName, String team, String role) {
      
        Students student = new Students(firstName,lastName,team,role);


        if(!containsStudent(firstName,lastName)){
            students.add(student);
        
        }
       
    }
    
    
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
