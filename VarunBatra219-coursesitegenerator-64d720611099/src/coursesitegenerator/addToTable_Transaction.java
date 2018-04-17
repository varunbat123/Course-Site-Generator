/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;
import javafx.scene.control.TableView;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.workspace.TAWorkspace;


/**
 *
 * @author Varun
 */
public class addToTable_Transaction implements jTPS_Transaction {
    CourseSiteGeneratorApp app;
    jTPS j;
  TeachingAssistant ta;
  TAWorkspace workSpace;
  TAData data;
  String name;
  String email;
  int index;
  public addToTable_Transaction(CourseSiteGeneratorApp app,TeachingAssistant ta, int index){
      this.app = app;
       data = (TAData)app.getDataComponent();
       workSpace = (TAWorkspace)app.getWorkspaceComponent();
      name = ta.getName();
      this.index = index;
      email = ta.getEmail();
      this.ta = ta;
              
      
  }
    @Override
    public void doTransaction() {
    data.addTA(name, email);
  
    }

    @Override
    public void undoTransaction() {
          TableView taTable = workSpace.getTATable();
       
           taTable.getItems().remove(index);
    
    }
    
}
