/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Varun
 */
public class ScheduleData implements AppDataComponent {
   ObservableList<Schedules> schedules; 
   
   public ScheduleData(){
      schedules = FXCollections.observableArrayList();  
   } 
   
   public ObservableList getSchedules(){
    return this.schedules;
}
   
   
   public void addSchedule(String type,String date, String time, String title, String topic, String link, String criteria) {
      
        Schedules schedule = new Schedules(type,date,time,title, topic, link, criteria);

 if (!containsSchedule(date,title)) {
            schedules.add(schedule);
        
        }
            

       
    }
 public boolean containsSchedule(String testDate,String testTitle) {
        for (Schedules s: schedules) {
            if ((s.getDate().equals(testDate))&&(s.getTitle().equals(testTitle))) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
