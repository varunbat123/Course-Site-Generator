/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.Team;
import coursesitegenerator.workspace.ProjectWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class deleteTeam_Transaction implements jTPS_Transaction{
int index;  
CourseSiteGeneratorApp app;
  Team t;
  TAWorkspace workspace;
  TAData data;
  String name;
  String link;
  String color;
  String textColor;
  Color colorPick;
  Color textColorPick;
  ProjectWorkspace pWorkspace;
  ProjectData pData;
  public deleteTeam_Transaction(CourseSiteGeneratorApp app , int index, Team t){
      this.app = app;
      this.index = index;
      this.t= t;
      this.workspace = (TAWorkspace)app.getWorkspaceComponent();
      this.data= (TAData)app.getDataComponent();
      this.pWorkspace = workspace.getProjectWorkspace();
      this.pData = data.getProjectData();
      this.name = t.getName();
      this.link = t.getLink();
      this.color = t.getColor();
      this.textColor = t.getText();
      this.colorPick= t.getColor1();
      this.textColorPick = t.getColor2();
      
  }
  
    @Override
    public void doTransaction() {
  TableView teamTable = pWorkspace.getTeamView();
       teamTable.getItems().remove(index);
    }

    @Override
    public void undoTransaction() {
pData.addTeam(name, color, textColor, link);
Team t = (Team)data.getProjectData().getTeams().get(data.getProjectData().getTeams().size()-1);
t.setColor1(colorPick);
t.setColor2(textColorPick);
    }
    
}
