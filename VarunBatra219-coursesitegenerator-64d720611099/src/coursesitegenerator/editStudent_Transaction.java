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
public class editStudent_Transaction implements jTPS_Transaction{
    
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

        String oldFirstName;
        String oldLastName;
        String oldRole;
        String oldTeam;
        
        int index ;
    
    public editStudent_Transaction( CourseSiteGeneratorApp app,
    Students s,
        String firstName,
        String lastName,
        String role,
        String team,

        String oldFirstName,
        String oldLastName,
        String oldRole,
        String oldTeam,
        
        int index ){
     this.app= app;
     this.s=s;
     this.firstName= firstName;
     this.lastName= lastName;
     this.role= role;
     this.team= team;
     this.oldFirstName= oldFirstName;
     this.oldLastName= oldLastName;
     this.oldRole= oldRole;
     this.oldTeam = oldTeam;
     this.workspace = (TAWorkspace)app.getWorkspaceComponent();
     this.data = (TAData)app.getDataComponent();
     pWorkspace = workspace.getProjectWorkspace();
     pData = data.getProjectData();
        
        this.index= index;
        
    
}
    @Override
    public void doTransaction() {
  TableView studentTable = pWorkspace.getStudentView();
    s.setFirstname(firstName);
    s.setLastName(lastName);
    s.setRole(role);
    s.setTema(team);
       studentTable.getItems().set(index, s);
    }

    @Override
    public void undoTransaction() {
TableView studentTable = pWorkspace.getStudentView();
    s.setFirstname(oldFirstName);
    s.setLastName(oldLastName);
    s.setRole(oldRole);
    s.setTema(oldTeam);
       studentTable.getItems().set(index, s);

    }
    
}
