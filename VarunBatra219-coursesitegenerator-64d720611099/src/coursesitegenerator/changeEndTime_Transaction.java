/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import java.util.HashMap;
import jtps.jTPS_Transaction;
import coursesitegenerator.data.TAData;
import coursesitegenerator.workspace.TAWorkspace;

/**
 *
 * @author Varun
 */
public class changeEndTime_Transaction implements jTPS_Transaction{
CourseSiteGeneratorApp app;
TAWorkspace workspace;
TAData dataComponent;
HashMap<String,String> saveCellValues;
int newEndHours;
int oldEndHour;
public changeEndTime_Transaction(CourseSiteGeneratorApp app, TAData dataComponent,TAWorkspace workspace, HashMap<String,String> saveCellValues,int newEndHours, int oldEndHour){
        this.app = app;
        this.dataComponent = dataComponent;
        this.workspace = workspace;
        this.saveCellValues = saveCellValues;
        this.newEndHours= newEndHours;
        this.oldEndHour = oldEndHour;
    }

    @Override
    public void doTransaction() {
    dataComponent.setEndHour(newEndHours);
      workspace.getOfficeHoursGridPane().getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
               if(dataComponent.getOfficeHours().containsKey(key)){
                   dataComponent.setCellValue(key, (String)saveCellValues.get(key));
               }
           }
    }

    @Override
    public void undoTransaction() {
      dataComponent.setEndHour(oldEndHour);
      workspace.getOfficeHoursGridPane().getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
               if(dataComponent.getOfficeHours().containsKey(key)){
                   dataComponent.setCellValue(key, (String)saveCellValues.get(key));
               }
           }
    
    
    }
 


}
