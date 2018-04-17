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
public class RecitationData implements AppDataComponent {
    ObservableList<Recitations> recitations;
  

   public RecitationData(){
      recitations = FXCollections.observableArrayList();  
   } 
   
   public ObservableList getRecitations(){
    return this.recitations;
}
   
   
   public void addRecitation(String section, String instructor,String day, String location, String supervisor1, String supervisor2) {
      
        Recitations recitation = new Recitations(section,instructor,day,location,supervisor1, supervisor2);


        
            recitations.add(recitation);
        

       
    }
    public boolean containsTA(String testName) {
        for (Recitations r : recitations) {
            if (r.getSection().equals(testName)) {
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
