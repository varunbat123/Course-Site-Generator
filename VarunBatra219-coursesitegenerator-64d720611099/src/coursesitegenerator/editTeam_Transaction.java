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
public class editTeam_Transaction implements jTPS_Transaction {
CourseSiteGeneratorApp app;
 int index;
   
 Team t;

  TAWorkspace workspace;
  TAData data;
  String name;
  String link;
  String color;
  String textColor;
  Color colorPick;
  Color textColorPick;
  String oldName;
  String oldLink;
  String oldColor;
  String oldTextColor;
  Color oldColorPick;
  Color oldTextColorPick;
  ProjectWorkspace pWorkspace;
  ProjectData pData;
  public editTeam_Transaction(CourseSiteGeneratorApp app,int index, Team t,String name,  String link, String color, String textColor, Color colorPick, Color textColorPick, String oldName,String oldLink,String oldColor,String oldTextColor,Color oldColorPick,Color oldTextColorPick){
  this.app=app;
  this.index= index;
  this.t=t;
  this.name = name;
  this.link = link;
  this.color = color;
  this.textColor= textColor;
  this.colorPick= colorPick;
  this.textColorPick= textColorPick;
  this.oldName= oldName;
  this.oldLink= oldLink;
  this.oldColor= oldColor;
  this.oldTextColor = oldTextColor;
  this.oldColorPick = oldColorPick;
  this.oldTextColorPick = oldTextColorPick;
  workspace = (TAWorkspace)app.getWorkspaceComponent();
  data = (TAData)app.getDataComponent();
  pWorkspace = workspace.getProjectWorkspace();
  pData = data.getProjectData();
  
         
  }
  
    @Override
    public void doTransaction() {
  TableView taTable = pWorkspace.getTeamView();
     t.setName(name);
     t.setLink(link);
     t.setColor1(colorPick);
     t.setColor(color);
     t.setText(textColor);
     t.setColor2(textColorPick);
       taTable.getItems().set(index, t);

    }

    @Override
    public void undoTransaction() {
  TableView taTable = pWorkspace.getTeamView();
     t.setName(oldName);
     t.setLink(oldLink);
     t.setColor1(oldColorPick);
     t.setColor(oldColor);
     t.setText(oldTextColor);
     t.setColor2(oldTextColorPick);
       taTable.getItems().set(index, t);

    }
    
}
