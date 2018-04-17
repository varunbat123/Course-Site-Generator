/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.workspace;

import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.RecitationProp;
import static coursesitegenerator.RecitationProp.MISSING_DAY_TITLE;
import static coursesitegenerator.RecitationProp.MISSING_INSTRUCTOR_TITLE;
import static coursesitegenerator.RecitationProp.MISSING_LOCATION_TITLE;
import static coursesitegenerator.RecitationProp.MISSING_SECTION_NAME_TITLE;
import static coursesitegenerator.RecitationProp.NOT_UNIQUE_RECITATION;
import coursesitegenerator.addStudent_Transaction;
import coursesitegenerator.addTeam_Transaction;
import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.Students;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.Team;
import coursesitegenerator.deleteStudent_Transaction;
import coursesitegenerator.deleteTeam_Transaction;
import coursesitegenerator.editStudent_Transaction;
import coursesitegenerator.editTeam_Transaction;
import djf.ui.AppMessageDialogSingleton;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import jtps.jTPS;
import properties_manager.PropertiesManager;

/**
 *
 * @author Varun
 */
public class ProjectController {
CourseSiteGeneratorApp app;
     jTPS j = new jTPS();
    ProjectController(CourseSiteGeneratorApp app) {
        this.app = app;
    }

    public void handleAddTeam() {
        int index =0;
TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        ProjectWorkspace work = workspace.getProjectWorkspace();
        TextField teamText = work.nameText;
        
        String teamName = teamText.getText();
       TextField linkText = work.linkText;
        String link = linkText.getText();
        
        ColorPicker color = work.colorPick;
       String colorPick = color.getValue().toString();
       ColorPicker colorTextPick = work.textColorPick;
      
       String textColor = colorTextPick.getValue().toString();
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
  
        
        
        
       
        // EVERYTHING IS FINE, ADD A NEW team
       System.out.println(teamName);
            // ADD THE NEW Team TO THE DATA
            data.getProjectData().addTeam(teamName, colorPick, textColor, link);
           Team t = (Team)data.getProjectData().getTeams().get(data.getProjectData().getTeams().size()-1);
           t.setColor1(color.getValue());
           t.setColor2(colorTextPick.getValue());
           index = work.getTeamView().getItems().indexOf(t);
          j.addTransaction(new addTeam_Transaction(app,  t, index));
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
         ProjectWorkspace pWorkspace = workspace.getProjectWorkspace();
            pWorkspace.linkText.setText("");
         pWorkspace.nameText.setText("");
         ObservableList<Team> teams =data.getProjectData().getTeams();
            for (Team team : teams){
                workspace.getProjectWorkspace().teamText.getItems().add(team.getName());
                
                
            }       
        
    }
   public EventHandler<KeyEvent> redoHandler =
        new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                
                   final KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
                    if (redo.match(event)){
                        j.doTransaction();
                    }
                }
            };
     public EventHandler<KeyEvent> undoHandler =
        new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                
                   final KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
                    if (undo.match(event)){
                        j.undoTransaction();
                    }
                }
            };

   public void editTeam() {
     PropertiesManager props = PropertiesManager.getPropertiesManager();
         TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         ProjectWorkspace pWorkspace = workSpace.getProjectWorkspace();
         pWorkspace.addButton.setText("Update");
          TableView teamTable = workSpace.getProjectWorkspace().teams;
           Team t= (Team) teamTable.getSelectionModel().getSelectedItem();
           String name = t.getName();
           javafx.scene.paint.Color color = t.getColor1();
           String link = t.getLink();
           javafx.scene.paint.Color text = t.getColor2();
          
          
           pWorkspace.nameText.setText(name);
           pWorkspace.colorPick.setValue(color);
           pWorkspace.textColorPick.setValue(text);
           pWorkspace.linkText.setText(link);
     
           
           
        
           pWorkspace.addButton.setOnAction(e->{
              this.performEditTeam(t, pWorkspace);  
           });
       
       
   }

    private void performEditTeam(Team t, ProjectWorkspace pWorkspace) {
        String name;
        Color color;
        Color text;
        String colorText;
        String textColor;
        String link;
        String oldName;
        Color oldColor;
        Color oldText;
        String oldColorText;
        String oldTextColor;
        String oldLink;
        int index;

      PropertiesManager props = PropertiesManager.getPropertiesManager();
        // This is to access datA
        TAData data = (TAData)app.getDataComponent();
        ProjectData pData = data.getProjectData();
        // Access the table
        TableView teamTable = pWorkspace.teams;
      
         oldName= t.getName();
         oldColor = t.getColor1();
         oldText = t.getColor2();
         oldColorText = t.getColor();
         oldTextColor= t.getText();
         oldLink= t.getLink();
    
       t.setColor(pWorkspace.colorPick.getValue().toString());
       t.setLink(pWorkspace.linkText.getText());
       t.setName(pWorkspace.nameText.getText());
       t.setText(pWorkspace.textColorPick.getValue().toString());
       t.setColor1(pWorkspace.colorPick.getValue());
       t.setColor2(pWorkspace.textColorPick.getValue());
       
        name= t.getName();
         color = t.getColor1();
         text = t.getColor2();
         colorText = t.getColor();
         textColor= t.getText();
         link= t.getLink();
          
         index =teamTable.getSelectionModel().getSelectedIndex();
         j.addTransaction(new editTeam_Transaction( app, index,  t, name,  link,  colorText,  textColor,  color,  text,  oldName, oldLink, oldColorText, oldTextColor, oldColor, oldText));
         //teamTable.getItems().set(teamTable.getSelectionModel().getSelectedIndex(), t); 
        
        pWorkspace.linkText.setText("");
         pWorkspace.nameText.setText("");
            pWorkspace.addButton.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         pWorkspace.addButton.setOnAction(e->{
             this.handleAddTeam();
         });
        
        
        
        
        
        
    }
    
  public EventHandler<KeyEvent> keyEventHandler =
        new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                  PropertiesManager props = PropertiesManager.getPropertiesManager();
                int index=0;
                if ((keyEvent.getCode() == KeyCode.DELETE)||(keyEvent.getCode()== KeyCode.MINUS)) {
                 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
                 TableView teamTable = workspace.getProjectWorkspace().getTeamView();
                    Team t= (Team) teamTable.getSelectionModel().getSelectedItem();
                    index = teamTable.getSelectionModel().getSelectedIndex();
                
                
                // recitationTable.getItems().remove(recitationTable.getSelectionModel().getSelectedItem());
               
                    j.addTransaction(new deleteTeam_Transaction(app,index,t));
           
   
                }
 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
                 ProjectWorkspace pWorkspace = workspace.getProjectWorkspace();
                      pWorkspace.addButton.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         pWorkspace.addButton.setOnAction(e->{
             handleAddTeam();
         });
     app.getGUI().updateToolbarControls(false);	
   
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
    pWorkspace.linkText.setText("");
         pWorkspace.nameText.setText("");
        
           
                
                }  
            
        };   

   public void handleAddStudent() {
       int index =0;
TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        ProjectWorkspace work = workspace.getProjectWorkspace();
        TextField firstNameText = work.firstNameText;
        
        String firstName = firstNameText.getText();
       TextField lastNameText = work.lastNameText;
        String lastName = lastNameText.getText();
        
        ChoiceBox teamText = work.teamText;
       String teamName = teamText.getSelectionModel().getSelectedItem().toString();
       TextField roleText = work.roleText;
      
       String role = roleText.getText();
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
  
        
        
        
       
        // EVERYTHING IS FINE, ADD A NEW team
     
            // ADD THE NEW Team TO THE DATA
            data.getProjectData().addStudent(firstName, lastName, teamName, role);
            Students s = (Students) data.getProjectData().getStudents().get(data.getProjectData().getStudents().size()-1);
           index = work.getStudentView().getItems().indexOf(s);
          j.addTransaction(new addStudent_Transaction(app,  index, s));
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
       
 ProjectWorkspace pWorkspace = workspace.getProjectWorkspace();
           pWorkspace.firstNameText.setText("");
         pWorkspace.lastNameText.setText("");
       
         pWorkspace.roleText.setText("");   
        
       
       
       
       
       
       
       
   }

    public void editStudent() {
PropertiesManager props = PropertiesManager.getPropertiesManager();
         TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         ProjectWorkspace pWorkspace = workSpace.getProjectWorkspace();
         pWorkspace.addButton2.setText("Update");
          TableView studentTable = workSpace.getProjectWorkspace().students;
           Students s= (Students) studentTable.getSelectionModel().getSelectedItem();
           String firstName = s.getFirstName();
           String lastName = s.getLastName();
           String team = s.getTeam();
           String role = s.getRole();
           
          
          
           pWorkspace.teamText.getSelectionModel().select(team);
           pWorkspace.roleText.setText(role);
           pWorkspace.lastNameText.setText(lastName);
           pWorkspace.firstNameText.setText(firstName);
     
           
           
        
           pWorkspace.addButton2.setOnAction(e->{
              this.performEditStudent(s, pWorkspace);  
           });


    }

    private void performEditStudent(Students s, ProjectWorkspace pWorkspace) {
        String firstName;
        String lastName;
        String role;
        String team;

        String oldFirstName;
        String oldLastName;
        String oldRole;
        String oldTeam;
        
        int index =0;
        
      PropertiesManager props = PropertiesManager.getPropertiesManager();
        // This is to access datA
        TAData data = (TAData)app.getDataComponent();
        ProjectData pData = data.getProjectData();
        // Access the table
        TableView studentTable = pWorkspace.students;
      
         oldFirstName = s.getFirstName();
         oldLastName= s.getLastName();
         oldRole= s.getRole();
         oldTeam= s.getTeam();
    
      s.setFirstname(pWorkspace.firstNameText.getText());
      s.setLastName(pWorkspace.lastNameText.getText()); 
      s.setTema(pWorkspace.teamText.getSelectionModel().getSelectedItem().toString());
      s.setRole(pWorkspace.roleText.getText());
     
      firstName = s.getFirstName();
      lastName = s.getLastName();
      team = s.getTeam();
      role = s.getRole();
          
         index =studentTable.getSelectionModel().getSelectedIndex();
j.addTransaction(new editStudent_Transaction( app, s,firstName,lastName,role,team,oldFirstName,oldLastName,oldRole,oldTeam,index));
        pWorkspace.firstNameText.setText("");
         pWorkspace.lastNameText.setText("");
         pWorkspace.roleText.setText("");
         
            pWorkspace.addButton2.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         pWorkspace.addButton.setOnAction(e->{
             this.handleAddStudent();
         });
  
        
    }
    
   
    
    
      public EventHandler<KeyEvent> keyEventHandler2 =
        new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
               
                int index=0;
                if ((keyEvent.getCode() == KeyCode.DELETE)||(keyEvent.getCode()== KeyCode.MINUS)) {
                 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
                 TableView studentTable = workspace.getProjectWorkspace().getStudentView();
                    Students s= (Students) studentTable.getSelectionModel().getSelectedItem();
                    index = studentTable.getSelectionModel().getSelectedIndex();
                
                
                // recitationTable.getItems().remove(recitationTable.getSelectionModel().getSelectedItem());
               
                    j.addTransaction(new deleteStudent_Transaction(app,s, index));
   
                }
                PropertiesManager props = PropertiesManager.getPropertiesManager();
 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
 ProjectWorkspace pWorkspace = workspace.getProjectWorkspace();
         pWorkspace.addButton2.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         pWorkspace.addButton2.setOnAction(e->{
             handleAddStudent();
         });
         pWorkspace.firstNameText.setText("");
         pWorkspace.lastNameText.setText("");
       
         pWorkspace.roleText.setText("");
     app.getGUI().updateToolbarControls(false);	
   
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
   
        
           
                
                }  
            
        };

   public  void handleClear1() {
       PropertiesManager props = PropertiesManager.getPropertiesManager();
 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
 ProjectWorkspace pWorkspace = workspace.getProjectWorkspace();
pWorkspace.linkText.setText("");
         pWorkspace.nameText.setText("");
            pWorkspace.addButton.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         pWorkspace.addButton.setOnAction(e->{
             this.handleAddTeam();
         });
    }

    public void handleClear2() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
 ProjectWorkspace pWorkspace = workspace.getProjectWorkspace();
 pWorkspace.firstNameText.setText("");
         pWorkspace.lastNameText.setText("");
         pWorkspace.roleText.setText("");
         
            pWorkspace.addButton2.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         pWorkspace.addButton.setOnAction(e->{
             this.handleAddStudent();
         });

    }

   
    
}
