/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.workspace;

import coursesitegenerator.CourseInfoProp;
import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.RecitationProp;
import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.Recitations;
import coursesitegenerator.data.TAData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
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
public class RecitationWorkspace extends AppWorkspaceComponent{
    CourseSiteGeneratorApp app;
    RecitationController controller;
    Label pageHeader;
    Label subPaneHeader;
    Label section;
    Label instructor;
    Label day;
    Label location;
    Label supervisor;
    Label supervisor2;
    Button add;
    Button clear;
    TableView<Recitations> recitations;
    TableColumn<Recitations, String> sectionColumn;
    TableColumn<Recitations, String> locationColumn;
    TableColumn<Recitations, String> instructorColumn;
    TableColumn<Recitations, String> dayColumn;
    TableColumn<Recitations, String> supervisorColumn;
    TableColumn<Recitations, String> supervisor2Column;
    TextField sectionText;
    TextField instructorText;
    TextField dayText;
    TextField locationText;
    ChoiceBox supervisorChoice;
    ChoiceBox supervisor2Choice;
    
    public Pane workspace;
    GridPane container;
    GridPane secondPane;
    
    
      public RecitationWorkspace(CourseSiteGeneratorApp app){
         this.app= app;
         controller = new RecitationController(app);
           PropertiesManager props = PropertiesManager.getPropertiesManager();
 // Table View
 add = new Button(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
 clear = new Button (props.getProperty(RecitationProp.CLEAR_BUTTON.toString()));
 recitations = new TableView();
 
    
        recitations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getDataComponent();
        RecitationData recitationData = data.getRecitationData();
        ObservableList<Recitations> tableData = recitationData.getRecitations();
        recitations.setItems(tableData); 
      sectionColumn = new TableColumn(props.getProperty(RecitationProp.SECTION_COLUMN.toString()));
       sectionColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("section")
        );
 locationColumn= new TableColumn(props.getProperty(RecitationProp.LOCATION_COLUMN.toString()));
 locationColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("location")
        );
 instructorColumn= new TableColumn(props.getProperty(RecitationProp.INSTRUCTOR_COLUMN.toString()));
 instructorColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("instructor")
        );
  dayColumn= new TableColumn(props.getProperty(RecitationProp.DAY_COLUMN.toString()));
  dayColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("date")
        );
 supervisorColumn= new TableColumn(props.getProperty(RecitationProp.SUPERVISOR_COLUMN.toString()));
 supervisorColumn.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("supervisor1")
        );
 supervisor2Column= new TableColumn(props.getProperty(RecitationProp.SUPERVISOR_COLUMN.toString()));
 supervisor2Column.setCellValueFactory(
                new PropertyValueFactory<Recitations, String>("supervisor2")
        );
    recitations.getColumns().add(sectionColumn);
    recitations.getColumns().add(locationColumn);
    recitations.getColumns().add(dayColumn);
    recitations.getColumns().add(instructorColumn);
    recitations.getColumns().add(supervisorColumn);
    recitations.getColumns().add(supervisor2Column);     
           
           
           
           
      //second Pane
      
      //labels
      pageHeader = new Label(props.getProperty(RecitationProp.RECITATION_HEADER.toString()));
section = new Label(props.getProperty(RecitationProp.SECTION_LABEL.toString()));
instructor =new Label(props.getProperty(RecitationProp.INSTRUCTOR_LABEL.toString()));
 day =new Label(props.getProperty(RecitationProp.DAY_LABEL.toString()));
 location =new Label(props.getProperty(RecitationProp.LOCATION_LABEL.toString()));
 supervisor =new Label(props.getProperty(RecitationProp.SUPERVISOR_LABEL.toString()));
 supervisor2 =new Label(props.getProperty(RecitationProp.SUPERVISOR_LABEL.toString()));
 // text fields
 sectionText = new TextField();
 
instructorText = new TextField();
 dayText = new TextField();
 locationText = new TextField(); 
// ChoiceBoxes 
 supervisorChoice = new ChoiceBox();    
supervisor2Choice = new ChoiceBox();    
      secondPane = new GridPane();
      secondPane.setHgap(10);
secondPane.setVgap(10);
secondPane.setPadding(new Insets(25,25,25,25));
 secondPane.add(section, 0, 0);
 secondPane.add(instructor, 0, 1);
 secondPane.add(day, 0, 2);
 secondPane.add(location, 0, 3);
 secondPane.add(supervisor, 0, 4);
 secondPane.add(supervisor2, 0, 5);
 secondPane.add(sectionText, 6, 0);
 secondPane.add(instructorText, 6, 1);
 secondPane.add(dayText, 6, 2);
 secondPane.add(locationText, 6, 3);
 secondPane.add(supervisorChoice, 6, 4);
 secondPane.add(supervisor2Choice, 6, 5);
  secondPane.add(add, 0, 6);  
 secondPane.add(clear, 6, 6); 
  container = new GridPane();
  container = new GridPane();
      container.setHgap(10);
container.setVgap(10);
container.setPadding(new Insets(25,25,25,25));
container.add(pageHeader, 65, 0);
container.add(recitations, 65, 1);
container.add(secondPane, 65, 2);
  workspace = new BorderPane();
((BorderPane) workspace) .setCenter(container);
   add.setOnAction(e->{
      controller.handleAddRecitation(); 
   });
   recitations.setOnKeyPressed(controller.keyEventHandler);// if the delete key is pressed then delete the rcitation
   recitations.setOnMouseClicked(e->{
         controller.editRecitation();
         app.getGUI().updateToolbarControls(true);
     });
   this.clear.setOnAction(e->{
       controller.handleClear();
   });
   workspace.setOnKeyReleased(controller.undoHandler);
    container.setOnKeyReleased(controller.redoHandler);       
      }      
    
    public TableView getTableView(){
        return this.recitations;
    }

    @Override
    public void resetWorkspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
