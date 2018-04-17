/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import java.util.List;
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
public class deleteTA_Transaction implements jTPS_Transaction{
 CourseSiteGeneratorApp app;
 int index;
    jTPS j;
  TeachingAssistant ta;
  
  TAWorkspace workSpace;
  TAData data;
  String name;
  String email;
  List<Label> labels;
  
  public deleteTA_Transaction(CourseSiteGeneratorApp app,TeachingAssistant ta, int index, List<Label> labels){
      this.labels = labels;
      this.app = app;
      this.index = index;
       data = (TAData)app.getDataComponent();
       workSpace = (TAWorkspace)app.getWorkspaceComponent();
       this.name = ta.getName();
       this.email = ta.getEmail();
   this.ta = ta;
  
  
        
      
  }
    @Override
    public void doTransaction() {
       
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
         TableView taTable = workspace.getTATable();
         taTable.getItems().remove(index);
           for(Label l:labels){
         if(l.getText().contains(name)){// if the label has the same name as the ta
            l.setText(l.getText().replaceAll(name, "    ")); // replace only the taName in the label
         }
         
   
           }

        
     
        
       
    }

    @Override
    public void undoTransaction() {
         data.addTA(name, email);
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
    
         for(Label l: labels){
         if(l.getText().contains("    ")){// if the label has the same name as the ta
            l.setText(l.getText().replaceAll( "    ",name)); // replace only the taName in the label
         }
         
         }
    
    } 
}
    
