/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.workspace;

import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.ProjectInfoProp;
import coursesitegenerator.RecitationProp;
import coursesitegenerator.ScheduleProp;
import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.Students;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.Team;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

/**
 *
 * @author Varun
 */
public class ProjectWorkspace extends AppWorkspaceComponent {
    ProjectController controller;
    ColorPicker textColorPick;
    ColorPicker colorPick;
    Label pageHeader;
    Label teamHeader;
    Label add;
    Label name;
    Label color;
    Label textColor;
    Label link;
    Label studentsHeader;
    Label add2;
    Label firstName;
    Label lastName;
    Label team;
    Label role;
    TableView<Team> teams;
    TableColumn<Team, String> nameColumn;
    TableColumn<Team, String> colorColumn;
    TableColumn<Team, String> textColorColumn;
    TableColumn<Team, String> linkColumn;
    TableView<Students> students;
    TableColumn<Students, String> firstNameColumn;
    TableColumn<Students, String> lastNameColumn;
    TableColumn<Students, String> teamColumn;
    TableColumn< Students, String> roleColumn;
    Button addButton;
    Button clearButton;
    Button addButton2;
    Button clearButton2;
    TextField nameText;
    TextField linkText;
    TextField firstNameText;
    TextField lastNameText;
    ChoiceBox teamText;
    TextField roleText;
    Pane workspace;
    GridPane firstPane;
    GridPane secondPane;
    SplitPane s;
    
    public ProjectWorkspace(CourseSiteGeneratorApp app){
        controller = new ProjectController(app);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
     // labels
     teamHeader = new Label (props.getProperty(ProjectInfoProp.TEAM_HEADER.toString()));
     add = new Label (props.getProperty(ScheduleProp.ADD_EDIT.toString()));
     add2 = new Label (props.getProperty(ScheduleProp.ADD_EDIT.toString()));
     name = new Label (props.getProperty(ProjectInfoProp.NAME_LABEL.toString()));
     color = new Label (props.getProperty(ProjectInfoProp.COLOR_LABEL.toString()));
     textColor = new Label (props.getProperty(ProjectInfoProp.TEXT_COLOR_LABEL.toString()));
     link = new Label(props.getProperty(ScheduleProp.LINK_LABEL.toString()));
     studentsHeader= new Label (props.getProperty(ProjectInfoProp.STUDENT_HEADER.toString()));
     firstName = new Label (props.getProperty(ProjectInfoProp.FIRST_NAME_LABEL.toString()));
     lastName = new Label (props.getProperty(ProjectInfoProp.LAST_NAME_LABEL.toString()));
     team = new Label (props.getProperty(ProjectInfoProp.TEAM_LABEL.toString()));
     role = new Label (props.getProperty(ProjectInfoProp.ROLE_LABEL.toString()));
     textColorPick = new ColorPicker();
     colorPick = new ColorPicker();
       teams = new TableView();
       teams.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getDataComponent();
       ProjectData projectData = data.getProjectData();
        ObservableList<Team> tableData = projectData.getTeams();
        teams.setItems(tableData);
       nameColumn = new TableColumn (props.getProperty(ProjectInfoProp.NAME_COLUMN.toString()));
         nameColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("name")
        );
       colorColumn = new TableColumn (props.getProperty(ProjectInfoProp.COLOR_COLUMN.toString()));
         colorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("color")
        );
       textColorColumn = new TableColumn (props.getProperty(ProjectInfoProp.TEXT_COLOR_COLUMN.toString()));
         textColorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("text")
        );
       linkColumn = new TableColumn (props.getProperty(ProjectInfoProp.LINK_COLUMN.toString()));
         linkColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("link")
        );
       teams.getColumns().add(nameColumn);
       teams.getColumns().add(colorColumn);
       teams.getColumns().add(textColorColumn);
       teams.getColumns().add(linkColumn);
       nameText = new TextField();
       linkText = new TextField();
       addButton= new Button (props.getProperty(RecitationProp.ADD_BUTTON.toString()));
       clearButton = new Button (props.getProperty(RecitationProp.CLEAR_BUTTON.toString()));
         firstPane = new GridPane();
           firstPane.setHgap(10);
firstPane.setVgap(10);
firstPane.setPadding(new Insets(25,25,25,25));
firstPane.add(teamHeader, 0, 0);
firstPane.add(teams, 0, 1);
firstPane.add(add, 0, 4);
firstPane.add(name, 0, 5);
firstPane.add(nameText, 4, 5);
firstPane.add(color, 0, 6);
firstPane.add(colorPick, 1, 6);
firstPane.add(textColor, 6, 6);
firstPane.add(textColorPick, 7, 6);
firstPane.add(link, 0, 7);
firstPane.add(linkText, 4, 7);
firstPane.add(addButton, 0, 8);
firstPane.add(clearButton, 4, 8);
// secondPane

secondPane = new GridPane();
secondPane.setHgap(10);
secondPane.setVgap(10);
secondPane.setPadding(new Insets(25,25,25,25));
// student Table
students = new TableView();
students.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       
        ObservableList<Students> tableData2 = projectData.getStudents();
        students.setItems(tableData2);
firstNameColumn = new TableColumn (props.getProperty(ProjectInfoProp.FIRST_COL.toString()));
         firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("firstName")
        );
lastNameColumn = new TableColumn (props.getProperty(ProjectInfoProp.LAST_COL.toString()));
 lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("lastName")
        );
teamColumn = new TableColumn (props.getProperty(ProjectInfoProp.TEAM_COL.toString()));
 teamColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("team")
        );
roleColumn = new TableColumn (props.getProperty(ProjectInfoProp.ROLE_COL.toString()));
 roleColumn.setCellValueFactory(
                new PropertyValueFactory<Students, String>("role")
        );
students.getColumns().add(firstNameColumn);
students.getColumns().add(lastNameColumn);
students.getColumns().add(teamColumn);
students.getColumns().add(roleColumn);
// text fields
firstNameText = new TextField();
lastNameText = new TextField();
teamText = new ChoiceBox();
roleText = new TextField();

// buttons
addButton2= new Button (props.getProperty(RecitationProp.ADD_BUTTON.toString()));
clearButton2 = new Button (props.getProperty(RecitationProp.CLEAR_BUTTON.toString()));


secondPane.add(studentsHeader, 0, 0);
secondPane.add(students, 0, 1);
secondPane.add(add2, 0, 3);
secondPane.add(firstName, 0, 4);
secondPane.add(lastName, 0, 5);
secondPane.add(team, 0, 6);
secondPane.add(role, 0, 7);
secondPane.add(firstNameText, 4, 4);
secondPane.add(lastNameText, 4, 5);
secondPane.add(teamText, 4, 6);
secondPane.add(roleText, 4, 7);
secondPane.add(addButton2, 0, 8);
secondPane.add(clearButton2, 4, 8);

 
s = new SplitPane(firstPane,secondPane);
workspace = new BorderPane();
((BorderPane) workspace) .setCenter(s);
 
      addButton.setOnAction(e->{
          controller.handleAddTeam();
      });
     teams.setOnMouseClicked(e->{
         controller.editTeam();
         app.getGUI().updateToolbarControls(true);
     });
       teams.setOnKeyPressed(controller.keyEventHandler);
    workspace.setOnKeyReleased(controller.undoHandler);
    s.setOnKeyReleased(controller.redoHandler);       
       
      addButton2.setOnAction(e->{
          controller.handleAddStudent();
      });
       students.setOnMouseClicked(e->{
         controller.editStudent();
         app.getGUI().updateToolbarControls(true);
     });
       students.setOnKeyPressed(controller.keyEventHandler2);
      clearButton.setOnAction(e->{
          controller.handleClear1();
      });
     clearButton2.setOnAction(e->{
         controller.handleClear2();
     });
    }
    

    @Override
    public void resetWorkspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public TableView getTeamView() {
   return this.teams;
    }

    public TableView getStudentView() {
return this.students;
        }
    
}
