/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.workspace;

import coursesitegenerator.CourseInfoProp;
import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.RecitationProp;
import coursesitegenerator.ScheduleProp;
import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.TAData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
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
public class ScheduleWorkspace extends AppWorkspaceComponent {
     ScheduleController controller;
    Label pageHeader;
    Label calendarBoundaries;
    Label startingDate;
    Label endingDate;
    Label subPaneheader;
    Label add;
    Label type;
    Label date;
    Label time;
    Label title;
    Label topic;
    Label link;
    Label criteria;
    Button addButton;
    Button clearButton;
    DatePicker startingChoice;
    DatePicker endingChoice;
    ChoiceBox typeChoice;
    TextField dateChoice;
    TableView<Schedules> scheduleTable;
    TableColumn<Schedules, String> typeColumn;
    TableColumn<Schedules, String> dateColumn;
    TableColumn<Schedules, String> titleColumn;
    TableColumn<Schedules, String> topicColumn;
    TextField  timeText;
    TextField titleText;
    TextField topicText;
    TextField linkText;
    TextField criteriaText;
    Pane workspace;
    GridPane firstPane;
    GridPane secondPane;
    GridPane container;
    GridPane thirdPane;
    SplitPane s;
    
    
    
    
            
    
    
    public ScheduleWorkspace(CourseSiteGeneratorApp app){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        controller = new ScheduleController(app);
        // firstPane
        firstPane = new GridPane();
           firstPane.setHgap(10);
firstPane.setVgap(10);
firstPane.setPadding(new Insets(25,25,25,25));
        //labels
        calendarBoundaries = new Label (props.getProperty(ScheduleProp.CALENDAR_BOUNDARIES.toString()));
        startingDate = new Label (props.getProperty(ScheduleProp.STARTING_DATE.toString()));
        endingDate = new Label (props.getProperty(ScheduleProp.ENDING_DATE.toString()));
        startingChoice = new DatePicker();
        endingChoice = new DatePicker();
        firstPane.add(calendarBoundaries, 0, 0);
        firstPane.add(startingDate, 0, 1);
        firstPane.add(startingChoice, 1, 1);
        firstPane.add(endingDate, 6, 1);
        firstPane.add(endingChoice, 7, 1);
        
     // secondPane 
     secondPane = new GridPane();
      secondPane.setHgap(10);
secondPane.setVgap(10);
secondPane.setPadding(new Insets(25,25,25,25));
     //labels
        subPaneheader = new Label (props.getProperty(ScheduleProp.SCHEDULE_SUB_PANE_HEADER.toString())); 
        add = new Label (props.getProperty(ScheduleProp.ADD_EDIT.toString()));
        type = new Label (props.getProperty(ScheduleProp.TYPE_LABEL.toString()));
        date = new Label (props.getProperty(ScheduleProp.DATE_LABEL.toString()));
        time = new Label (props.getProperty(ScheduleProp.TIME_LABEL.toString()));
        title = new Label (props.getProperty(CourseInfoProp.TITLE_LABEL.toString()));
        topic = new Label (props.getProperty(ScheduleProp.TOPIC_LABEL.toString()));
        link = new Label (props.getProperty(ScheduleProp.LINK_LABEL.toString()));
        criteria = new Label (props.getProperty(ScheduleProp.CRITERIA_LABEL.toString()));
     // choiceBox
     
     typeChoice = new ChoiceBox();
     typeChoice.getItems().add("holiday");
     typeChoice.getItems().add("lecture");
     typeChoice.getItems().add("recitation");
     typeChoice.getItems().add("test");
     dateChoice = new TextField();
     // text fields
     timeText = new TextField();
     titleText = new TextField();
     topicText = new TextField();
     linkText = new TextField();
     criteriaText = new TextField();
     // buttons
     addButton = new Button ((props.getProperty(RecitationProp.ADD_BUTTON.toString())));
     clearButton = new Button ((props.getProperty(RecitationProp.CLEAR_BUTTON.toString())));
     
        // table
        scheduleTable = new TableView();
          scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getDataComponent();
        ScheduleData scheduleData = data.getScheduleData();
        ObservableList<Schedules> tableData = scheduleData.getSchedules();
        scheduleTable.setItems(tableData);
        typeColumn = new TableColumn (props.getProperty(ScheduleProp.TYPE_COLUMN.toString()));
         typeColumn.setCellValueFactory(
                new PropertyValueFactory<Schedules, String>("type")
        );
        dateColumn = new TableColumn (props.getProperty(ScheduleProp.DATE_COLUMN.toString()));
         dateColumn.setCellValueFactory(
                new PropertyValueFactory<Schedules, String>("date")
        );
        titleColumn = new TableColumn (props.getProperty(CourseInfoProp.TITLE_LABEL.toString()));
         titleColumn.setCellValueFactory(
                new PropertyValueFactory<Schedules, String>("title")
        );
        topicColumn = new TableColumn (props.getProperty(ScheduleProp.TOPIC_COLUMN.toString()));
         topicColumn.setCellValueFactory(
                new PropertyValueFactory<Schedules, String>("topic")
        );
        scheduleTable.getColumns().add(typeColumn);
        scheduleTable.getColumns().add(dateColumn);
        scheduleTable.getColumns().add(titleColumn);
        scheduleTable.getColumns().add(topicColumn);
        
       
        secondPane.add(add, 0, 4);
        secondPane.add(type, 0, 5);
        secondPane.add(date, 0, 6);
        secondPane.add(time, 0, 7);
        secondPane.add(title, 0, 8);
        secondPane.add(topic, 0, 9);
        secondPane.add(link, 0,10 );
        secondPane.add(criteria, 0, 11);
        secondPane.add(typeChoice, 4, 5);
        secondPane.add(dateChoice, 4, 6);
        secondPane.add(timeText, 4, 7);
        secondPane.add(titleText, 4, 8);
        secondPane.add(topicText, 4, 9);
        secondPane.add(linkText, 4, 10);
        secondPane.add(criteriaText, 4, 11);
        secondPane.add(addButton, 4, 12);
        secondPane.add(clearButton, 5, 12);
        
        
        container = new GridPane();
      container.setHgap(10);
container.setVgap(10);
container.setPadding(new Insets(25,25,25,25));
          thirdPane = new GridPane();
      thirdPane.setHgap(10);
thirdPane.setVgap(10);
thirdPane.setPadding(new Insets(25,25,25,25));
    thirdPane.add(subPaneheader, 0, 0);
        thirdPane.add(scheduleTable, 0, 1);     
        container.add(firstPane, 0, 0);
        container.add(secondPane,0, 1);
        s = new SplitPane(container,thirdPane);
        
       workspace = new BorderPane();
       scheduleTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));
       scheduleTable.prefWidthProperty().bind(workspace.widthProperty());
       ((BorderPane) workspace) .setCenter(s);
     addButton.setOnAction(e->{
       controller.handleAddSchedule();  
     });
     scheduleTable.setOnMouseClicked(e->{
         controller.editSchedule();
         app.getGUI().updateToolbarControls(true);
     });
     endingChoice.setOnAction(e->{
         controller.checkDate();
     });
      scheduleTable.setOnKeyPressed(controller.keyEventHandler);// if the delete key is pressed then delete the rcitation
      workspace.setOnKeyReleased(controller.undoHandler);
    container.setOnKeyReleased(controller.redoHandler);    
        clearButton.setOnAction(e->{
            controller.handleClear();
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

    public TableView getTableView() {
        return this.scheduleTable;
    }
    
}
