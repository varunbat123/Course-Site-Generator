/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.Students;
import coursesitegenerator.data.TAData;
import coursesitegenerator.workspace.ProjectWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class addStudent_Transaction implements jTPS_Transaction{
    CourseSiteGeneratorApp app;
 Students s;
  TAWorkspace workSpace;
  TAData data;
  String firstName;
  String lastName;
  String team;
  String role;
 
  int index; 
  ProjectWorkspace pWorkspace;
  ProjectData pData;
  
    
public addStudent_Transaction(CourseSiteGeneratorApp app, int index, Students s){
    this.app =app;
    this.index = index;
    this.s = s;
    this.firstName= s.getFirstName();
    this.lastName = s.getLastName();
    this.team = s.getTeam();
    this.role = s.getRole();
    workSpace =(TAWorkspace)app.getWorkspaceComponent();
    data = (TAData)app.getDataComponent();
    pWorkspace = workSpace.getProjectWorkspace();
    pData = data.getProjectData();
    
    
    
}
    @Override
    public void doTransaction() {
pData.addStudent(firstName, lastName, team, role);

    }

    @Override
    public void undoTransaction() {
TableView studentTable = pWorkspace.getStudentView();
studentTable.getItems().remove(index);
    }
    
}
