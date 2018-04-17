/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.Recitations;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.workspace.RecitationWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class deleteRecitation_Transaction implements jTPS_Transaction{
     CourseSiteGeneratorApp app;
 int index;
    jTPS j;
 Recitations r;
  
  TAWorkspace workSpace;
  TAData data;
  RecitationWorkspace rWorkspace;
  RecitationData rData;
  String instructor, section, date, supervisor1, supervisor2, location;
public deleteRecitation_Transaction(CourseSiteGeneratorApp app, int index, Recitations r){
    this.app = app;
    this.index = index;
    this.r = r;
    this.workSpace = (TAWorkspace) app.getWorkspaceComponent();
    this.data = (TAData) app.getDataComponent();
    rWorkspace = workSpace.getRecitationWorkspace();
    rData = data.getRecitationData();
    this.instructor= r.getInstructor();
    this.section = r.getSection();
    this.date= r.getDate();
    this.supervisor1= r.getSupervisor1();
    this.supervisor2= r.getSupervisor2();
}
    @Override
    public void doTransaction() {
    TableView recitationTable = rWorkspace.getTableView();
         recitationTable.getItems().remove(index);
    }

    @Override
    public void undoTransaction() {
       rData.addRecitation(section, instructor, date, location, supervisor1, supervisor2);
    }
    
}
