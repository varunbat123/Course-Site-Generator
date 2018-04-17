/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import java.util.HashMap;
import jtps.jTPS_Transaction;
import coursesitegenerator.data.TAData;
import coursesitegenerator.workspace.TAController;
import coursesitegenerator.workspace.TAWorkspace;

/**
 *
 * @author Varun
 */
public class changeStartTime_Transaction implements jTPS_Transaction {
     CourseSiteGeneratorApp app;
    TAData dataComponent;
    TAWorkspace workspace;
    HashMap<String,String> saveCellValues;
    int newStartHours;
    int oldStartHour;
    boolean go;
   
    
    public changeStartTime_Transaction(CourseSiteGeneratorApp app, TAData dataComponent,TAWorkspace workspace, HashMap<String,String> saveCellValues, int newStartHours, int oldStartHour, boolean go){
        this.app = app;
        this.go = go;
        this.dataComponent = dataComponent;
        this.workspace = workspace;
        this.saveCellValues = saveCellValues;
        this.newStartHours = newStartHours;
        this.oldStartHour = oldStartHour;
    }

    @Override
    public void doTransaction() {
        TAController controller = new TAController(app);
        controller.handleChangeStartTime(go);
    
    }

    @Override
    public void undoTransaction() {
        this.newStartHours = this.oldStartHour;
        this.oldStartHour = this.newStartHours;
             if(newStartHours>oldStartHour){
               dataComponent.setStartHour(newStartHours);
          //Reload the workspace
          workspace.getOfficeHoursGridPane().getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
                

              int changeRow = newStartHours- oldStartHour;
              // this will update the cell key to the correct value we need it to be
             String newKey= dataComponent.setCellKeyToDifferent(key, changeRow);
               if(dataComponent.getOfficeHours().containsKey(key)){
                   // set the value of the cell key to the value that we need it to be (see setCellKeyToDifferent)
                   dataComponent.setCellValue(key, (String)saveCellValues.get(newKey));
               }
           }
            
            
            
            
        }
        
        
        
        
        
        
        
        
        
        
        else{
          dataComponent.setStartHour(newStartHours);
          //Reload the workspace
          workspace.getOfficeHoursGridPane().getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
                
    
              int changeRow = oldStartHour- newStartHours;
              // this will update the cell key to the correct value we need it to be
             String newKey= dataComponent.setCellKeyToDifferent(key, changeRow);
               if(dataComponent.getOfficeHours().containsKey(key)){
                   // set the value of the cell key to the value that we need it to be (see setCellKeyToDifferent)
                   dataComponent.setCellValue(newKey, (String)saveCellValues.get(key));
               }
           
              }
        
      
        }
  
    }
    
}
