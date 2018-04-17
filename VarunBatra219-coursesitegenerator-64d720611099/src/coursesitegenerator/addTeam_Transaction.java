/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.Team;
import coursesitegenerator.workspace.ProjectWorkspace;
import coursesitegenerator.workspace.ScheduleWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class addTeam_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorApp app;
    jTPS j;
 Team t;
  TAWorkspace workSpace;
  TAData data;
  String name;
  String color;
  String text;
  String link;
 
  int index; 
  ProjectWorkspace pWorkspace;
  ProjectData pData;
  
  public addTeam_Transaction(CourseSiteGeneratorApp app, Team t, int index){
      this.app =app;
      this.index = index;
      this. t = t;
      workSpace = (TAWorkspace)app.getWorkspaceComponent();
      data = (TAData) app.getDataComponent();
      pWorkspace = workSpace.getProjectWorkspace();
      pData = data.getProjectData();
      this.name = t.getName();
      this.color = t.getColor();
      this.text = t.getText();
      this.link = t.getLink();
              
      
  }

    @Override
    public void doTransaction() {
pData.addTeam(name, color, text, link);
    
    }

    @Override
    public void undoTransaction() {
   TableView teamTable = pWorkspace.getTeamView();
       
           teamTable.getItems().remove(index);
    }
  
  
  
}
