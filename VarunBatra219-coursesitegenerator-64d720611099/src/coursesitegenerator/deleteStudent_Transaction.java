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
public class deleteStudent_Transaction implements jTPS_Transaction {
  TAWorkspace workspace;
  TAData data;
  ProjectWorkspace pWorkspace;
  ProjectData pData;
  CourseSiteGeneratorApp app;
    Students s;
        String firstName;
        String lastName;
        String role;
        String team;
        int index ;
     public deleteStudent_Transaction(CourseSiteGeneratorApp app, Students s, int index){
       this.app = app;
       this.s =s;
       this.index = index;
       this.workspace = (TAWorkspace)app.getWorkspaceComponent();
       this.data = (TAData)app.getDataComponent();
       pWorkspace = workspace.getProjectWorkspace();
       pData= data.getProjectData();
       this.firstName= s.getFirstName();
       this.lastName = s.getLastName();
       this.role = s.getRole();
       this.team= s.getTeam();
       
     }   
    @Override
    public void doTransaction() {
TableView studentTable = pWorkspace.getStudentView();
       studentTable.getItems().remove(index);

    }

    @Override
    public void undoTransaction() {
        pData.addStudent(firstName, lastName, team, role);

    }
    
}
