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
import static coursesitegenerator.ScheduleProp.END_DATE_ERROR;
import static coursesitegenerator.TAManagerProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static coursesitegenerator.TAManagerProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import coursesitegenerator.addSchedule_Transaction;
import coursesitegenerator.data.Recitations;
import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.deleteSchedule_Transaction;
import coursesitegenerator.editRecitation_Transaction;
import coursesitegenerator.editSchedule_Transaction;
import djf.ui.AppMessageDialogSingleton;
import java.time.LocalDate;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import properties_manager.PropertiesManager;

/**
 *
 * @author Varun
 */
public class ScheduleController {
       CourseSiteGeneratorApp app;
     jTPS j = new jTPS();
    
    public ScheduleController(CourseSiteGeneratorApp app){
        this.app = app;
        

    }

   public void handleAddSchedule() {
     TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
     int index =0;
        ScheduleWorkspace work = workspace.getScheduleWorkspace();
        TextField dateField = work.dateChoice;
        String date = dateField.getText();
       TextField topicTextField = work.topicText;
        String topic = topicTextField.getText();
        
        TextField timeTextField = work.timeText;
        String time = timeTextField.getText();
        
        TextField criteriaTextField = work.criteriaText;
        String criteria = criteriaTextField.getText();
        
        TextField linkTextField = work.linkText;
        String link = linkTextField.getText();
        
        TextField titleTextField = work.titleText;
        String title = titleTextField.getText();
        
         ChoiceBox typeChoice = work.typeChoice;
        String type= typeChoice.getSelectionModel().getSelectedItem().toString();
        
         
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A type
        if (type.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_NAME_TITLE), props.getProperty(MISSING_SECTION_NAME_TITLE));  
           typeChoice.requestFocus();
        }
        // what about instructor?
        if (time.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(MISSING_INSTRUCTOR_TITLE));   
            timeTextField.requestFocus();
        }
          if (topic.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_TITLE));   
           topicTextField.requestFocus();
        }
           if (title.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TITLE), props.getProperty(MISSING_DAY_TITLE));   
           titleTextField.requestFocus();
        }
           if (date.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TITLE), props.getProperty(MISSING_DAY_TITLE));   
           dateField.requestFocus();
        }
           if (link.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TITLE), props.getProperty(MISSING_DAY_TITLE));   
           linkTextField.requestFocus();
        }
           if (criteria.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TITLE), props.getProperty(MISSING_DAY_TITLE));   
           criteriaTextField.requestFocus();
        }
       
        // CHECK IF THE CHOICE BOXES ARE SELECTED 
        
        
        
       
        // EVERYTHING IS FINE, ADD A NEW schedule
        else {
          
           data.getScheduleData().addSchedule(type, date, time, title, topic, link, criteria);
            Schedules s = (Schedules)data.getScheduleData().getSchedules().get(data.getScheduleData().getSchedules().size()-1);
              index = work.getTableView().getItems().indexOf(s);
           j.addTransaction(new addSchedule_Transaction(app,s,index));
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
        
              
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

    public void editSchedule() {
       PropertiesManager props = PropertiesManager.getPropertiesManager();
         TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         ScheduleWorkspace sWorkspace = workSpace.getScheduleWorkspace();
         sWorkspace.addButton.setText("Update");
          TableView scheduleTable = workSpace.getScheduleWorkspace().scheduleTable;
           Schedules s= (Schedules) scheduleTable.getSelectionModel().getSelectedItem();
           String sType = s.getType();
           String sTitle = s.getTitle();
           String sDate = s.getDate();
           String sTime = s.getTime();
           String sTopic = s.getTopic();
           String sLink = s.getLink();
           String sCriteria = s.getCriteria();
           sWorkspace.typeChoice.getSelectionModel().select(sType);
           sWorkspace.titleText.setText(sTitle);
           
        sWorkspace.dateChoice.setText(sDate);
           sWorkspace.timeText.setText(sTime);
         sWorkspace.topicText.setText(sTopic);
          sWorkspace.linkText.setText(sLink);
          sWorkspace.criteriaText.setText(sCriteria);
           sWorkspace.addButton.setOnAction(e->{
              this.performEditSchedule(s, sWorkspace);  
           });
    }

    private void performEditSchedule(Schedules s, ScheduleWorkspace sWorkspace) {
     String type;
        String title;
        String date;
        String topic;
        String link;
        String time;
        String criteria;
        String oldType;
        String oldTitle;
        String oldDate;
        String oldTopic;
        String oldLink;
        String oldTime;
        String oldCriteria;
          int index;
          
          
           // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        // This is to access datA
        TAData data = (TAData)app.getDataComponent();
        ScheduleData sData = data.getScheduleData();
        // Access the table
        TableView scheduleTable = sWorkspace.scheduleTable;
      
        
    
       
       
         // pass the old Schedule before we alter it
         oldType= s.getType();
         oldTitle = s.getTitle();
        oldDate= s.getDate();
        oldTopic= s.getTopic();
         oldLink = s.getLink();
        oldTime = s.getTime();
         oldCriteria = s.getCriteria();
          
        s.setType(sWorkspace.typeChoice.getSelectionModel().getSelectedItem().toString());
        s.setTitle(sWorkspace.titleText.getText());
        s.setDate(sWorkspace.dateChoice.getText());
        s.setTopic(sWorkspace.topicText.getText());
        s.setLink(sWorkspace.linkText.getText());
        s.setTime(sWorkspace.timeText.getText());
        s.setCriteria(sWorkspace.criteriaText.getText());
        
         type = s.getType();
         title = s.getTitle();
         date = s.getDate();
         topic = s.getTopic();
         link = s.getLink();
         time = s.getTime();
         criteria  = s.getCriteria();
        index =scheduleTable.getSelectionModel().getSelectedIndex();
         scheduleTable.getItems().set(scheduleTable.getSelectionModel().getSelectedIndex(), s); 
           // The function is done set every thing back to normal
           sWorkspace.titleText.setText("");
           sWorkspace.timeText.setText("");
           sWorkspace.topicText.setText("");
           sWorkspace.dateChoice.setText("");
           sWorkspace.linkText.setText("");
           sWorkspace.criteriaText.setText("");
          
            sWorkspace.addButton.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         sWorkspace.addButton.setOnAction(e->{
             this.handleAddSchedule();
         });
         sWorkspace.titleText.requestFocus();
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();
  j.addTransaction(new editSchedule_Transaction( type,  title,  date,  topic,  link,  time,  criteria,   oldType, oldTitle,  oldDate,  oldTopic, oldLink, oldTime, oldCriteria,  index, app,s));      
        
           
    }
    
    
    
     public EventHandler<KeyEvent> keyEventHandler =
        new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
               
                int index=0;
                if ((keyEvent.getCode() == KeyCode.DELETE)||(keyEvent.getCode()== KeyCode.MINUS)) {
                 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
                 TableView scheduleTable = workspace.getScheduleWorkspace().getTableView();
                    Schedules s= (Schedules) scheduleTable.getSelectionModel().getSelectedItem();
                    index = scheduleTable.getSelectionModel().getSelectedIndex();
                
                
                // recitationTable.getItems().remove(recitationTable.getSelectionModel().getSelectedItem());
               
                    j.addTransaction(new deleteSchedule_Transaction(app,index,s));
   
                }

        
     app.getGUI().updateToolbarControls(false);	
   
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
   
        
           
                
                }  
            
        };

   public void checkDate() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
ScheduleWorkspace s = workspace.getScheduleWorkspace();
if(s.endingChoice.getValue().getYear()<s.startingChoice.getValue().getYear()){
    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(END_DATE_ERROR), props.getProperty(END_DATE_ERROR));  
    s.endingChoice.setValue(s.startingChoice.getValue());
}
else if(s.endingChoice.getValue().getYear()==s.startingChoice.getValue().getYear()){
    

if(s.endingChoice.getValue().getMonthValue()<s.startingChoice.getValue().getMonthValue()){
   AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(END_DATE_ERROR), props.getProperty(END_DATE_ERROR));  
   s.endingChoice.setValue(s.startingChoice.getValue());
}
else if(s.endingChoice.getValue().getMonthValue()==s.startingChoice.getValue().getMonthValue()){
 if(s.endingChoice.getValue().getDayOfMonth()<s.startingChoice.getValue().getDayOfMonth()){
  AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
dialog.show(props.getProperty(END_DATE_ERROR), props.getProperty(END_DATE_ERROR));  
   s.endingChoice.setValue(s.startingChoice.getValue());
} 
 
}

}
 
   }

    public void handleClear() {
          PropertiesManager props = PropertiesManager.getPropertiesManager();
TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
ScheduleWorkspace sWorkspace = workspace.getScheduleWorkspace();
           sWorkspace.titleText.setText("");
           sWorkspace.timeText.setText("");
           sWorkspace.topicText.setText("");
           sWorkspace.dateChoice.setText("");
           sWorkspace.linkText.setText("");
           sWorkspace.criteriaText.setText("");
          
            sWorkspace.addButton.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         sWorkspace.addButton.setOnAction(e->{
             this.handleAddSchedule();
         });

    }
    
    
    
    
    
}
