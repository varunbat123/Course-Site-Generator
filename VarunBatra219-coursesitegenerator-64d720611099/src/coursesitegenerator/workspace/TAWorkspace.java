package coursesitegenerator.workspace;

import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.TAManagerProp;
import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.TAData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static javax.management.Query.times;
import properties_manager.PropertiesManager;

import coursesitegenerator.style.TAStyle;

import coursesitegenerator.data.TeachingAssistant;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * This class serves as the workspace component for the TA Manager
 * application. It provides all the user interface controls in 
 * the workspace area.
 * 
 * @author Richard McKenna
 * @coAuthor Varun Batra
 */
public class TAWorkspace extends AppWorkspaceComponent {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CourseSiteGeneratorApp app;
    CourseInfoWorkspace courseWorkspace;
    RecitationWorkspace recitationWorkspace;
    ScheduleWorkspace scheduleWorkspace;
    ProjectWorkspace projectWorkspace;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TAController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    CourseInfoWorkspace c ;
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    TableColumn<TeachingAssistant, Boolean> checkColumn;
    

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    // end and start time boxes
    ChoiceBox start ;
         ChoiceBox end;
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;

    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public TAWorkspace(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(TAManagerProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        ObservableList<String> time = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
        start = new ChoiceBox();
        end = new ChoiceBox();
        start.setItems(time);
        end.setItems(time);
        taTable.setItems(tableData);
        
        String nameColumnText = props.getProperty(TAManagerProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(TAManagerProp.EMAIL_COLUMN_TEXT.toString());
        checkColumn = new TableColumn (props.getProperty(TAManagerProp.UNDERGRAD_COLUMN.toString()));
        checkColumn.setCellFactory(tc-> new CheckBoxTableCell());
        nameColumn = new TableColumn(nameColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
         taTable.getColumns().add(checkColumn);
        taTable.getColumns().add(nameColumn);
        emailColumn = new TableColumn(emailColumnText);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(emailColumn);
        

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(TAManagerProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(TAManagerProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button("Clear");
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(TAManagerProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
      // officeHoursHeaderBox.getChildren().add(start);
       //officeHoursHeaderBox.getChildren().add(end);
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
        VBox outerPane = new VBox();
        HBox outerLabels = new HBox();
        outerLabels.getChildren().add(new Label("Start")); 
        outerLabels.getChildren().add(new Label("        End"));
        
        HBox outer = new HBox();
        outer.getChildren().add(start);
        end.setLayoutX(start.getLayoutX()+100);
        end.setLayoutY(start.getLayoutY());
        outer.getChildren().add(end);
        outerPane.getChildren().add(outerLabels);
        outerPane.getChildren().add(outer);
        outerPane.setMaxWidth(200);
        
          
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane),outerPane);
        workspace = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
      
        TabPane tabs = new TabPane();
        Tab course = new Tab();
        course.setText("Course Info");
         courseWorkspace = new CourseInfoWorkspace(app);
        
        course.setContent(courseWorkspace.workspace);
  
        Tab TA = new Tab();
        TA.setText(props.getProperty(TAManagerProp.TA_TAB.toString()));
        TA.setContent(sPane);
        Tab recitation = new Tab();
       recitationWorkspace = new RecitationWorkspace(app);
        recitation.setText(props.getProperty(TAManagerProp.RECITATION_TAB.toString()));
       
        recitation.setContent(recitationWorkspace.workspace);
        Tab schedule = new Tab();
        schedule.setText(props.getProperty(TAManagerProp.SCHEDULE_TAB.toString()));
        scheduleWorkspace = new ScheduleWorkspace(app);
        schedule.setContent(scheduleWorkspace.workspace);
        Tab project = new Tab();
        project.setText(props.getProperty(TAManagerProp.PROJECT_TAB.toString()));
        projectWorkspace = new ProjectWorkspace(app);
        project.setContent(projectWorkspace.workspace);
        tabs.getTabs().add(course);
        tabs.getTabs().add(TA);
        tabs.getTabs().add(recitation);
        tabs.getTabs().add(schedule);
        tabs.getTabs().add(project);
    
((BorderPane) workspace).setCenter(tabs);
        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TAController(app);
   
        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();
        });
        clearButton.setOnAction(e -> {
            controller.handleClear();
        });
      taTable.setOnKeyPressed(controller.keyEventHandler);// if the delete key is pressed then delete the ta
      // if an event occurs inside ta table then perform edit TA options
    taTable.setOnMouseClicked(e->{
         controller.editTA();
         app.getGUI().updateToolbarControls(true);
     });
   workspace.setOnKeyReleased(controller.undoHandler);
    sPane.setOnKeyReleased(controller.redoHandler);
    end.getSelectionModel().selectedItemProperty().addListener(e ->{
        controller.handleChangeEndTime();
         app.getGUI().updateToolbarControls(true);
    });
     start.getSelectionModel().selectedItemProperty().addListener(e ->{
        controller.handleChangeStartTime(false);
         app.getGUI().updateToolbarControls(true);
    });
    }
    
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
     public Pane getCourseInfoBox(){
         return ((CourseInfoWorkspace)courseWorkspace).getCourseInfoBox();
    }
      public Pane getPageBox(){
         return ((CourseInfoWorkspace)courseWorkspace).getPageBox();
    }
       public Pane getSiteBox(){
         return ((CourseInfoWorkspace)courseWorkspace).getSiteBox();
    }
    public ChoiceBox getStart(){
        return start;
    }
    public ChoiceBox getEnd(){
        return end;
    }
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }
    public TextField getEmailTextField(){
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    @Override
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        TAData taData = (TAData)dataComponent;
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseClicked(e -> {
                
                controller.handleCellToggle((Pane) e.getSource());
             
            });
        }
           for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseEntered(e -> {
               
                
              controller.highlightRowAndColumn(p); // make the pane yelllow
             
            });
               for (Pane z : officeHoursGridTACellPanes.values()) {
            z.setOnMouseExited(e -> {
               
                
              controller.deHighlightRowAndColumn(z); // make the pane yelllow
             
            });
        }
           }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        TAStyle taStyle = (TAStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }
    public void removeFromGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
       officeHoursGridPane.getChildren().remove(cellPane);
   
        
        // remove it from panes
        panes.remove(cellKey);
        labels.remove(cellKey);
        TAData taData = (TAData)dataComponent;
        reloadOfficeHoursGrid(taData);
    
               
    }
     public CourseInfoWorkspace getCourseWorkspace() {
         return this.courseWorkspace;
    }
    public RecitationWorkspace getRecitationWorkspace() {
         return this.recitationWorkspace;
    }
     public ScheduleWorkspace getScheduleWorkspace() {
         return this.scheduleWorkspace;
    }
     public ProjectWorkspace getProjectWorkspace() {
         return this.projectWorkspace;
    }
    
}
