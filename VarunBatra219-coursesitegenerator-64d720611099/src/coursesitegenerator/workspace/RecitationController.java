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


import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.Recitations;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;

import coursesitegenerator.deleteRecitation_Transaction;

import coursesitegenerator.editRecitation_Transaction;
import djf.ui.AppMessageDialogSingleton;
import javafx.collections.ObservableList;

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
public class RecitationController {
     CourseSiteGeneratorApp app;
     jTPS j = new jTPS();
    
    public RecitationController(CourseSiteGeneratorApp app){
        this.app = app;
        

    }
    public EventHandler<KeyEvent> keyEventHandler =
        new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
               
                int index=0;
                if ((keyEvent.getCode() == KeyCode.DELETE)||(keyEvent.getCode()== KeyCode.MINUS)) {
                 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
                 TableView recitationTable = workspace.getRecitationWorkspace().getTableView();
                    Recitations r= (Recitations) recitationTable.getSelectionModel().getSelectedItem();
                    index = recitationTable.getSelectionModel().getSelectedIndex();
                
                
                // recitationTable.getItems().remove(recitationTable.getSelectionModel().getSelectedItem());
               
                    j.addTransaction(new deleteRecitation_Transaction(app,index,r));
   
                }

        
     app.getGUI().updateToolbarControls(false);	
   
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
   
        
           
                
                }  
            
        };

     public void handleAddRecitation() {
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        RecitationWorkspace work = workspace.getRecitationWorkspace();
        TextField sectionField = work.sectionText;
        String section = sectionField.getText();
       TextField instructorTextField = work.instructorText;
        String instructor = instructorTextField.getText();
        
        TextField dayTextField = work.dayText;
        String day = dayTextField.getText();
        
        TextField locationTextField = work.locationText;
        String location = locationTextField.getText();
        
         ChoiceBox supervisor1C = work.supervisorChoice;
        String supervisor1 = supervisor1C.getSelectionModel().getSelectedItem().toString();
        
         ChoiceBox supervisor2C = work.supervisor2Choice;
        String supervisor2 = supervisor2C.getSelectionModel().getSelectedItem().toString();
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A section
        if (section.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_NAME_TITLE), props.getProperty(MISSING_SECTION_NAME_TITLE));  
            sectionField.requestFocus();
        }
        // what about instructor?
        if (instructor.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(MISSING_INSTRUCTOR_TITLE));   
            instructorTextField.requestFocus();
        }
          if (location.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_TITLE));   
           locationTextField.requestFocus();
        }
           if (day.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_TITLE), props.getProperty(MISSING_DAY_TITLE));   
           dayTextField.requestFocus();
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.getRecitationData().containsTA(section)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(NOT_UNIQUE_RECITATION), props.getProperty(NOT_UNIQUE_RECITATION));  
            sectionField.setText("");
            instructorTextField.setText("");
            dayTextField.setText("");
            locationTextField.setText("");
           sectionField.requestFocus();
        }
        // CHECK IF THE CHOICE BOXES ARE SELECTED 
        
        
        
       
        // EVERYTHING IS FINE, ADD A NEW Recitation
        else {
            // ADD THE NEW TA TO THE DATA
            data.getRecitationData().addRecitation(section,instructor,day,location,supervisor1,supervisor2);
          
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
        
              
        }
    }

    public void editRecitation() {
       PropertiesManager props = PropertiesManager.getPropertiesManager();
         TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         RecitationWorkspace rWorkspace = workSpace.getRecitationWorkspace();
         rWorkspace.add.setText("Update");
          TableView recitationTable = workSpace.getRecitationWorkspace().recitations;
           Recitations r= (Recitations) recitationTable.getSelectionModel().getSelectedItem();
           String rSection = r.getSection();
           String rInstructor = r.getInstructor();
           String rDate = r.getDate();
           String rLoc = r.getLocation();
           String rSupervisor = r.getSupervisor1();
           String rSupervisor2 = r.getSupervisor2();
           rWorkspace.sectionText.setText(rSection);
           rWorkspace.instructorText.setText(rInstructor);
           rWorkspace.dayText.setText(rDate);
           rWorkspace.locationText.setText(rLoc);
           rWorkspace.supervisorChoice.getSelectionModel().select(rSupervisor);
           rWorkspace.supervisor2Choice.getSelectionModel().select(rSupervisor2);
           rWorkspace.add.setOnAction(e->{
              this.performEditRecitation(r, rWorkspace);  
           });
          
    }
    
    public void performEditRecitation(Recitations r, RecitationWorkspace workSpace){
        // these will be passed to the redo method
        
        //---------------
        String section;
        String instructor;
        String date;
        String location;
        String supervisor1;
        String supervisor2;
        String oldSection;
        String oldInstructor;
        String oldDate;
        String oldLocation;
        String oldSupervisor1;
        String oldSupervisor2;
          int index;
        //---------------------          
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        // This is to access datA
        TAData data = (TAData)app.getDataComponent();
        RecitationData rData = data.getRecitationData();
        // Access the table
        TableView recitationTable = workSpace.recitations;
      
        
    
       
       
         // pass the old TA before we alter it
         oldSection = r.getSection();
         oldInstructor = r.getInstructor();
         oldDate = r.getDate();
         oldLocation = r.getLocation();
         oldSupervisor1 = r.getSupervisor1();
         oldSupervisor2 = r.getSupervisor2();
         
         // Now update the ta Table
        r.setInstructor(workSpace.instructorText.getText());
        r.setSection(workSpace.sectionText.getText());
        r.setDate(workSpace.dayText.getText());
        r.setLocation(workSpace.locationText.getText());
        r.setSupervisor1(workSpace.supervisorChoice.getSelectionModel().getSelectedItem().toString());
        r.setSupervisor2(workSpace.supervisor2Choice.getSelectionModel().getSelectedItem().toString());
        section = r.getSection();
         instructor = r.getInstructor();
         date = r.getDate();
         location = r.getLocation();
         supervisor1 = r.getSupervisor1();
         supervisor2 = r.getSupervisor2();
        index =recitationTable.getSelectionModel().getSelectedIndex();
         recitationTable.getItems().set(recitationTable.getSelectionModel().getSelectedIndex(), r);
         // The function is done set every thing back to normal
         workSpace.sectionText.setText("");
         workSpace.dayText.setText("");
         workSpace.locationText.setText("");
         workSpace.instructorText.setText("");
         
         workSpace.add.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         workSpace.add.setOnAction(e->{
             this.handleAddRecitation();
         });
         workSpace.sectionText.requestFocus();
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();
  j.addTransaction(new editRecitation_Transaction( app,  r, index,  oldSupervisor1,  oldSupervisor2,supervisor1, supervisor2,  oldDate,  date,  oldInstructor, instructor,  oldLocation,  location,  oldSection,  section));      
        
      
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

   public void handleClear() {
PropertiesManager props = PropertiesManager.getPropertiesManager();
         TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
         RecitationWorkspace workSpace = workspace.getRecitationWorkspace();
   workSpace.sectionText.setText("");
         workSpace.dayText.setText("");
         workSpace.locationText.setText("");
         workSpace.instructorText.setText("");
         
         workSpace.add.setText(props.getProperty(RecitationProp.ADD_BUTTON.toString()));
         workSpace.add.setOnAction(e->{
             this.handleAddRecitation();
         });
   }

  
    
}
