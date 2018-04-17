/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.workspace.TAWorkspace;

/**
 *
 * @author Varun
 */
public class editTA_Transaction implements jTPS_Transaction{
 CourseSiteGeneratorApp app;
 int index;
    jTPS j;
  TeachingAssistant ta;
  TeachingAssistant oldTA;
  TAWorkspace workSpace;
  TAData data;
  String name;
  String email;
  String oldName;
  String oldEmail;
  public editTA_Transaction(CourseSiteGeneratorApp app,TeachingAssistant ta,String name,String email, String oldName, String oldEmail, int index){
      this.app = app;
       data = (TAData)app.getDataComponent();
       workSpace = (TAWorkspace)app.getWorkspaceComponent();
       this.name = name;
       this.email = email;
   this.ta = ta;
    this.oldName = oldName;
    this.oldEmail = oldEmail;
      this.index= index;
        
      
  }
    @Override
    public void doTransaction() {
      TableView taTable = workSpace.getTATable();
      ta.setName(name);
      ta.setEmail(email);
       taTable.getItems().set(index, ta);
                for (Pane p : workSpace.getOfficeHoursGridTACellPanes().values()) {// access each pane 
     for(int i =0; i<p.getChildren().size();i++){ // go through each label of the pane
        
         
       Label l = (Label)p.getChildren().get(i); 
       if(l.getText()!=null){
         if(l.getText().contains(oldName)){// if the label has the same name as the ta
            ((Label)p.getChildren().get(i)).setText(((Label)p.getChildren().get(i)).getText().replaceAll(oldName,name)); // replace only the taName in the label
         }
       }
     }
       
         }
    }

    @Override
    public void undoTransaction() {
      TableView taTable = workSpace.getTATable();
      ta.setName(oldName);
      ta.setEmail(oldEmail);
       taTable.getItems().set(index, ta);
             for (Pane p : workSpace.getOfficeHoursGridTACellPanes().values()) {// access each pane 
     for(int i =0; i<p.getChildren().size();i++){ // go through each label of the pane
        
         
       Label l = (Label)p.getChildren().get(i); 
       if(l.getText()!=null){
         if(l.getText().contains(name)){// if the label has the same name as the ta
            ((Label)p.getChildren().get(i)).setText(((Label)p.getChildren().get(i)).getText().replaceAll(name,oldName)); // replace only the taName in the label
         }
       }
     }
       
         }
       
       
    }
    
}
