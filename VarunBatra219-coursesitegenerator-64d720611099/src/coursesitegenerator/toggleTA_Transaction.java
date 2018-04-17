/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import javafx.beans.property.StringProperty;
import jtps.jTPS_Transaction;
import coursesitegenerator.data.TAData;

/**
 *
 * @author Varun
 */
public class toggleTA_Transaction implements jTPS_Transaction {
    String cellKey;
    String taName;
    CourseSiteGeneratorApp app;
    TAData data;
   
    boolean success;
    public toggleTA_Transaction(CourseSiteGeneratorApp app, String cellKey, String taName, boolean success){
        this.cellKey= cellKey;
        this.taName= taName;
        this.app = app;
        data = (TAData) app.getDataComponent();
        this.success = success;
    }

    @Override
    public void doTransaction() {
  if(success){
        StringProperty cell = data.getCell(cellKey);
          data.removeTAFromCell(cell, taName);
  }
  else{
      data.toggleTAOfficeHours(cellKey, taName);   
  }
  // now flip the values so we know what to do when we undo it
     if(success){
      this.success = false;
  }
  else{
      this.success = true;
  }
        
        
    }

    @Override
    public void undoTransaction() {
    if(success){
        StringProperty cell = data.getCell(cellKey);
          data.removeTAFromCell(cell, taName);
  }
  else{
      data.toggleTAOfficeHours(cellKey, taName);   
  }
    // now flip the values so we know what to do when we redo it
     if(success){
      this.success = false;
  }
  else{
      this.success = true;
  }
    }
    
}
