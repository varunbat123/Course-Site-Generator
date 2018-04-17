/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.workspace;
import coursesitegenerator.CourseInfoProp;
import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.data.CourseInfoData;
import coursesitegenerator.data.SiteData;
import coursesitegenerator.data.Sites;
import coursesitegenerator.data.TAData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Varun
 */
public class CourseInfoWorkspace extends AppWorkspaceComponent {
     // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CourseInfoController controller;
  BorderPane split;
    CourseSiteGeneratorApp app;
    ChoiceBox subjectChoice;
    ChoiceBox semesterChoice;
      ChoiceBox numberChoice;
        ChoiceBox yearChoice;
   ChoiceBox styleChoice;      
    Label banner;
    Label left;
    Label right;
    Label subject;
    ImageView bannerSelection;
    ImageView leftSelection;
    ImageView rightSelection;
    Label styleSheet;
          
    Label semester;
    Label title;
    Label instructorName;
    Label instructorHome;
    Label exportDir;
    Label exportDirSelection;
    Label number;
    Label year;
    Label siteHeader;
    Label siteTemplateSelection;
    Label courseInfoHeader;
    Label sitePages;
    Button change;
     Button change1;
      Button change2;
       Button change3;
    Button siteTemplateButton;
    GridPane courseInfoBox;
     GridPane siteTemplateBox;
     GridPane pageBox;
     SplitPane Page;
    HBox subjectBox;
    HBox semesterBox;
    HBox titleBox;
    HBox instructorNameBox;
    HBox instructorHomeBox;
    TextField titleText;
    TextField instructorNameText;
    TextField instructorHomeText;
    TableView siteTable;
    TableColumn<Sites, Boolean> useColumn;
    TableColumn<Sites, String> navColumn;
TableColumn<Sites, String> fileColumn;
    TableColumn<Sites, String> scriptColumn; 
    
   public Pane workspace;
 

 
    public CourseInfoWorkspace(CourseSiteGeneratorApp initApp){
        
    app = initApp;
    controller = new CourseInfoController(app);
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    // course workspace
workspace = new BorderPane();
// FIRST PANE_______________________________________________________________________________
// labels
exportDirSelection = new Label();
year = new Label(props.getProperty(CourseInfoProp.YEAR_LABEL.toString()));
number = new Label (props.getProperty(CourseInfoProp.NUMBER_LABEL.toString()));
title = new Label(props.getProperty(CourseInfoProp.TITLE_LABEL.toString()));
instructorName= new Label(props.getProperty(CourseInfoProp.INSTRUCTOR_NAME_LABEL.toString()));
instructorHome = new Label(props.getProperty(CourseInfoProp.INSTRUCTOR_HOME_LABEL.toString()));
semester = new Label(props.getProperty(CourseInfoProp.SEMESTER_LABEL.toString()));
subject= new Label(props.getProperty(CourseInfoProp.SUBJECT_LABEL.toString()));
courseInfoHeader = new Label(props.getProperty(CourseInfoProp.COURSE_HEADER.toString()));
exportDir = new Label(props.getProperty(CourseInfoProp.EXPORT_LABEL.toString()));

// layout
courseInfoBox = new GridPane();


subjectBox = new HBox();

subjectBox.getChildren().add(subject);
//Buttons
change = new Button(props.getProperty(CourseInfoProp.CHANGE_BUTTON.toString()));

// choice boxes
semesterChoice = new ChoiceBox();
semesterChoice.getItems().add("Fall");
subjectChoice= new ChoiceBox();
subjectChoice.getItems().add("CSE");
numberChoice = new ChoiceBox();
numberChoice.getItems().add("123");
yearChoice = new ChoiceBox();
yearChoice.getItems().add("123");

// textfields
titleText= new TextField();
instructorNameText = new TextField();
instructorHomeText = new TextField();
// Add to grid
courseInfoBox.setHgap(10);
courseInfoBox.setVgap(10);
courseInfoBox.setPadding(new Insets(25,25,25,25));
courseInfoBox.add(courseInfoHeader, 0, 0,2,1);
courseInfoBox.add(subjectBox, 0,1);
courseInfoBox.add(subjectChoice,6,1);
courseInfoBox.add(semester,0,2);
courseInfoBox.add(title,0,3);
courseInfoBox.add(instructorName,0,4);
courseInfoBox.add(instructorHome,0,5);
courseInfoBox.add(exportDir,0,6);
courseInfoBox.add(titleText,6,3);
courseInfoBox.add(semesterChoice,6,2);
courseInfoBox.add(instructorHomeText,6,5);
courseInfoBox.add(exportDirSelection,6,6);
courseInfoBox.add(instructorNameText,6,4);
courseInfoBox.add(number,9,0);
courseInfoBox.add(year,9,1);
courseInfoBox.add(numberChoice,12,0);
courseInfoBox.add(yearChoice,12,1);
courseInfoBox.add(change,12,6);

//_____________________________________________________________________ END OF FIRST PANE


// SECOND PANE_________________________________________________________
siteTemplateBox = new GridPane();
// labels
siteHeader = new Label(props.getProperty(CourseInfoProp.SITE_HEADER.toString()));
siteTemplateSelection = new Label();
sitePages = new Label (props.getProperty(CourseInfoProp.SITE_PAGES_LABEL.toString()));

//table

siteTable = new TableView();
 TAData data = (TAData) app.getDataComponent();
 SiteData siteData = data.getSiteData();
ObservableList<Sites> tableData = siteData.getSites();
        siteTable.setItems(tableData);
        siteTable.setEditable(true);
    String useColumnText = props.getProperty(CourseInfoProp.USE_COLUMN_TEXT.toString());
    String navColumnText = props.getProperty(CourseInfoProp.NAVBAR_COLUMN_TEXT.toString());
    String fileColumnText = props.getProperty(CourseInfoProp.FILE_COLUMN_TEXT.toString());
    String scriptColumnText = props.getProperty(CourseInfoProp.SCRIPT_COLUMN_TEXT.toString());
useColumn= new TableColumn(useColumnText);
useColumn.setCellFactory(tc-> new CheckBoxTableCell());
useColumn.setCellValueFactory(
                new PropertyValueFactory<Sites, Boolean>("use")
        );
navColumn= new TableColumn(navColumnText);
navColumn.setCellValueFactory(
                new PropertyValueFactory<Sites, String>("nav")
        );
fileColumn= new TableColumn(fileColumnText);
fileColumn.setCellValueFactory(
                new PropertyValueFactory<Sites, String>("file")
        );
scriptColumn= new TableColumn(scriptColumnText);
scriptColumn.setCellValueFactory(
                new PropertyValueFactory<Sites, String>("script")
        );
siteTable.getColumns().add(useColumn);
siteTable.getColumns().add(navColumn);
siteTable.getColumns().add(fileColumn);
siteTable.getColumns().add(scriptColumn);




// buttons
siteTemplateButton = new Button ((props.getProperty(CourseInfoProp.SITE_TEMPLATE_BUTTON.toString())));


// set up
siteTemplateBox.setHgap(10);
siteTemplateBox.setVgap(10);
siteTemplateBox.setPadding(new Insets(25,25,25,25));
siteTemplateBox.add(siteHeader, 0, 0);
siteTemplateBox.add(siteTemplateSelection, 0, 1);
siteTemplateBox.add(siteTemplateButton, 0, 2);
siteTemplateBox.add(sitePages, 0, 3);
siteTemplateBox.add(siteTable, 0, 4);


// END OF SECOND PANE

// 3rd PANE__________________________
pageBox= new GridPane();
// labels
  banner = new Label(props.getProperty(CourseInfoProp.BANNER_IMAGE_LABEL.toString()));
 left = new Label(props.getProperty(CourseInfoProp.LEFT_FOOTER_IMAGE_LABEL.toString()));
 right = new Label(props.getProperty(CourseInfoProp.RIGHT_FOOTER_IMAGE_LABEL.toString()));
bannerSelection= new ImageView();
 leftSelection = new ImageView();
 rightSelection = new ImageView();
 styleSheet = new Label(props.getProperty(CourseInfoProp.STYLE_SHEET_LABEL.toString()));
 
 // choice boxes
 styleChoice = new ChoiceBox();
 styleChoice.getItems().add("stylesheet.css");
 // set it up
 pageBox.setHgap(10);
pageBox.setVgap(10);
pageBox.setPadding(new Insets(25,25,25,25));
 pageBox.add(banner, 0, 0);
 pageBox.add(left, 0, 1); 
 pageBox.add(right, 0, 2); 
 pageBox.add(bannerSelection, 6, 0); 
 pageBox.add(leftSelection, 6, 1); 
 pageBox.add(rightSelection, 6, 2); 
  pageBox.add(styleSheet, 0, 3);
  pageBox.add(styleChoice, 6, 3);
  change1 = new Button(props.getProperty(CourseInfoProp.CHANGE_BUTTON.toString()));
  change2 = new Button(props.getProperty(CourseInfoProp.CHANGE_BUTTON.toString()));
  change3 = new Button(props.getProperty(CourseInfoProp.CHANGE_BUTTON.toString()));
pageBox.add(change1, 12, 0);
pageBox.add(change2, 12, 1);
pageBox.add(change3, 12, 2); 
  
 split = new BorderPane();
 split.setTop(courseInfoBox);
 split.setCenter(pageBox);
Page = new SplitPane(split, siteTemplateBox);

  siteTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));
       siteTable.prefWidthProperty().bind(workspace.widthProperty());
((BorderPane) workspace) .setCenter(Page);
change.setOnAction(e->{
    controller.handleChangeExportDir();
});
    change1.setOnAction(e->{
    controller.handleChangeBannerImage();
    });
    change2.setOnAction(e->{
    controller.handleChangeLeftImage();
    });
    change3.setOnAction(e->{
    controller.handleChangeRightImage();
    });
    siteTemplateButton.setOnAction(e->{
        controller.handleSetTemplateDirectory();
    });

       
    }
    public Pane getCourseInfoBox(){
        return this.courseInfoBox;
    }
    public Pane getPageBox(){
        return this.pageBox;
    }
     public Pane getSiteBox(){
        return this.siteTemplateBox;
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
