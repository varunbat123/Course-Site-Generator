package coursesitegenerator.workspace;

import static djf.settings.AppPropertyType.END_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.END_ERROR_TITLE;
import static djf.settings.AppPropertyType.OVERWRITE_MESSAGE;
import static djf.settings.AppPropertyType.OVERWRITE_TITLE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import static coursesitegenerator.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.style.TAStyle;
import static coursesitegenerator.style.TAStyle.CLASS_BRIGHTER_PANE;
import static coursesitegenerator.style.TAStyle.CLASS_CUSTOM_PANE;
import coursesitegenerator.workspace.TAWorkspace;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import coursesitegenerator.TAManagerProp;
import coursesitegenerator.addToTable_Transaction;
import coursesitegenerator.changeEndTime_Transaction;
import coursesitegenerator.changeStartTime_Transaction;
import coursesitegenerator.deleteTA_Transaction;
import coursesitegenerator.editTA_Transaction;
import coursesitegenerator.toggleTA_Transaction;
import javafx.collections.ObservableList;
/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @co author Varun Batra
 * @version 1.0
 */
public class TAController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CourseSiteGeneratorApp app;
    HashMap<String,String> saveCellValues = new HashMap<String,String>();
jTPS j = new jTPS();

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(CourseSiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
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
    
    
    // DELETE KEY DELETES TA FROM TABLE AND GRID
    public EventHandler<KeyEvent> keyEventHandler =
        new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                List<Label>  labels= new ArrayList<Label>();
                int index;
                if (keyEvent.getCode() == KeyCode.DELETE) {
                 TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
                 TableView taTable = workspace.getTATable();
                    TeachingAssistant ta= (TeachingAssistant) taTable.getSelectionModel().getSelectedItem();
                String taName = ta.getName();
                String x = "";
                
                 //taTable.getItems().remove(taTable.getSelectionModel().getSelectedItem());
               index =taTable.getSelectionModel().getSelectedIndex();
                   TAData data = (TAData)app.getDataComponent();
             for (Pane p : workspace.officeHoursGridTACellPanes.values()) {// access each pane 
     for(int i =0; i<p.getChildren().size();i++){ // go through each label of the pane
        
         
       Label l = (Label)p.getChildren().get(i); 
       if(l.getText()!=null){
         if(l.getText().contains(taName)){// if the label has the same name as the ta
             labels.add(l);
            ((Label)p.getChildren().get(i)).setText(((Label)p.getChildren().get(i)).getText().replaceAll(taName, "    ")); // replace only the taName in the label
         }
       }
   }
       

        
     app.getGUI().updateToolbarControls(false);	
   
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
   
        }
             j.addTransaction(new deleteTA_Transaction(app,ta, index,labels));
                }
                 TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
          workSpace.getNameTextField().setText("");
         workSpace.getEmailTextField().setText("");
         workSpace.addButton.setText("Add TA");
         workSpace.addButton.setOnAction(e->{
             handleAddTA();
         });
          workSpace.nameTextField.requestFocus();
            }
            
        };
    // if an existing TA is selected then we shoudl edit them
    public void editTA(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
         TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
          TableView taTable = workSpace.getTATable();
         
           TeachingAssistant ta= (TeachingAssistant) taTable.getSelectionModel().getSelectedItem(); // get the selected TA
                String taName = ta.getName();
                String taEmail = ta.getEmail();
               workSpace.nameTextField.setText(taName);
                workSpace.emailTextField.setText(taEmail); // set the TA email text field to the selected TA
                workSpace.addButton.setText(props.getProperty(TAManagerProp.UPDATE_TEXT.toString())); // change addTa to edit TA
                workSpace.addButton.setOnAction(e->{
                    this.performEditTA(ta, workSpace);
                });
    }
    public void performEditTA(TeachingAssistant ta, TAWorkspace workSpace){
        // these will be passed to the redo method
        //---------------
        String name;
        String email;
          String oldName;
          String oldEmail;
          int index;
        //---------------------          
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        // This is to access datA
        TAData data = (TAData)app.getDataComponent();
        // Access the table
        TableView taTable = workSpace.getTATable();
      // if the new email meets the standards then update the table ootherwise display error
        if   (!data.validate(workSpace.getEmailTextField().getText())){
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(INCORRECT_EMAIL), props.getProperty(INCORRECT_EMAIL));     
         workSpace.emailTextField.setText(ta.getEmail());
         workSpace.emailTextField.requestFocus();
        }
        else{ 
        // Great now update the office hours grid
         for (Pane p : workSpace.officeHoursGridTACellPanes.values()) {// access each pane 
     for(int i =0; i<p.getChildren().size();i++){ // go through each label of the pane
        
         
       Label l = (Label)p.getChildren().get(i); 
       if(l.getText()!=null){
         if(l.getText().contains(ta.getName())){// if the label has the same name as the ta
            ((Label)p.getChildren().get(i)).setText(((Label)p.getChildren().get(i)).getText().replaceAll(ta.getName(), workSpace.getNameTextField().getText())); // replace only the taName in the label
         }
       }
     }
       
         }
         // pass the old TA before we alter it
         oldName = ta.getName();
         oldEmail = ta.getEmail();
         // Now update the ta Table
        ta.setName(workSpace.getNameTextField().getText());
        ta.setEmail(workSpace.getEmailTextField().getText());
        name = ta.getName();
        email = ta.getEmail();
        index =taTable.getSelectionModel().getSelectedIndex();
         taTable.getItems().set(taTable.getSelectionModel().getSelectedIndex(), ta);
         // The function is done set every thing back to normal
         workSpace.getNameTextField().setText("");
         workSpace.getEmailTextField().setText("");
         workSpace.addButton.setText(props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString()));
         workSpace.addButton.setOnAction(e->{
             this.handleAddTA();
         });
         workSpace.nameTextField.requestFocus();
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();
  j.addTransaction(new editTA_Transaction(app, ta,name, email,oldName,oldEmail, index));      
        }
      
    }
    // Set the UI back to normal
     public void handleClear(){
          TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
          workSpace.getNameTextField().setText("");
         workSpace.getEmailTextField().setText("");
         workSpace.addButton.setText("Add TA");
         workSpace.addButton.setOnAction(e->{
             this.handleAddTA();
         });
          workSpace.nameTextField.requestFocus();
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();
     }
    public void highlightRowAndColumn(Pane p){
    
     TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
    
    for( String k:workSpace.officeHoursGridTACellPanes.keySet()){ // get all the rows and columns
 String [] row = k.split("_"); // split row and column into array
String [] pane= p.getId().split("_"); // same thing ^
 // travers the clomuns
     if((row[0].equals(pane[0])&&(Integer.parseInt(row[1])<Integer.parseInt(pane[1])))){ // if column is eqaul and row is less highlight it
          workSpace.officeHoursGridTACellPanes.get(k).getStyleClass().add(CLASS_BRIGHTER_PANE);
   
 }
       if((Integer.parseInt(row[0])<=Integer.parseInt((pane[0]))&&(Integer.parseInt(row[1])==Integer.parseInt(pane[1])))){ // if row is equal and column is less highlight it
          workSpace.officeHoursGridTACellPanes.get(k).getStyleClass().add(CLASS_BRIGHTER_PANE);
  
 }
 
 
    p.getStyleClass().add(CLASS_CUSTOM_PANE);// use the style for this pane
    }
    }
    public void deHighlightRowAndColumn(Pane p){
    
     TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
    
    for( String k:workSpace.officeHoursGridTACellPanes.keySet()){ // get all the rows and columns
 String [] row = k.split("_"); // split row and column into array
String [] pane= p.getId().split("_"); // same thing ^
 // travers the clomuns
     if((row[0].equals(pane[0])&&(Integer.parseInt(row[1])<Integer.parseInt(pane[1])))){ // if column is eqaul and row is less highlight it
          workSpace.officeHoursGridTACellPanes.get(k).getStyleClass().remove(CLASS_BRIGHTER_PANE);
   
     }
       if((Integer.parseInt(row[0])<=Integer.parseInt((pane[0]))&&(Integer.parseInt(row[1])==Integer.parseInt(pane[1])))){ // if row is equal and column is less highlight it
          workSpace.officeHoursGridTACellPanes.get(k).getStyleClass().remove(CLASS_BRIGHTER_PANE);
  
 }
 
 
    p.getStyleClass().remove(CLASS_CUSTOM_PANE);// use the style for this pane
    }
    }
     
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        int index;
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        String name = nameTextField.getText();
       TextField emailTextField = workspace.getEmailTextField();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));  
            nameTextField.requestFocus();
        }
        // what about email?
        if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));   
            emailTextField.requestFocus();
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name)|| data.containsTA(email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));  
            nameTextField.setText("");
            emailTextField.setText("");
            nameTextField.requestFocus();
        }
        else if   (!data.validate(email)){
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(INCORRECT_EMAIL), props.getProperty(INCORRECT_EMAIL));     
         emailTextField.setText("");
         emailTextField.requestFocus();
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            data.addTA(name,email);
             TableView taTable = workspace.getTATable();
         
           TeachingAssistant ta = data.getTA(name);
              index = taTable.getItems().indexOf(ta);
j.addTransaction(new addToTable_Transaction(app,ta, index));
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
             emailTextField.setText("");
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            app.getGUI().updateToolbarControls(false);
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
        
            // update the recitation choice box
             ObservableList<TeachingAssistant> teachers =data.getTeachingAssistants();
            for (TeachingAssistant tas : teachers){
                workspace.getRecitationWorkspace().supervisorChoice.getItems().add(tas.getName());
                workspace.getRecitationWorkspace().supervisor2Choice.getItems().add(tas.getName());
                
            }
              
        }
    
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
       
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        String taName = ta.getName();
        TAData data = (TAData)app.getDataComponent();
        String cellKey = pane.getId();
        
        
        // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
       String [] tas = data.getCellValue(cellKey).split("\n");
       boolean success = false;
  for(int i =1;i<tas.length;i++){
      if(tas[i].toString().equals(taName)){
          
   success= true;
   break;
      }
      else{
          success = false;
      }
      
  }
  
  j.addTransaction(new toggleTA_Transaction(app,cellKey,taName,success));
  app.getGUI().updateToolbarControls(false);
    
            app.getGUI().updatFileControllerNotSavedStatus();// we changed things so we have to let the app knwo work is not saved
      
    }
    public void handleChangeEndTime(){
        
           PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            TAData dataComponent = (TAData)app.getDataComponent();
            int oldEndHour = dataComponent.getEndHour();
            
            // Save all the Values from the panes
             for (Pane p : workspace.officeHoursGridTACellPanes.values()) {
             
             String cellKey = p.getId();
             this.saveCellValues.put(cellKey, dataComponent.getCellValue(cellKey));         
             }
       int endHour=  Integer.parseInt((String)workspace.end.getSelectionModel().getSelectedItem());
       if(dataComponent.getStartHour()>= endHour){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(END_ERROR_TITLE), props.getProperty(END_ERROR_MESSAGE));
              workspace.end.getSelectionModel().select(oldEndHour-1);
       }
       else{
          dataComponent.setEndHour(Integer.parseInt((String)workspace.end.getSelectionModel().getSelectedItem()));
        String selection= "";
          //Reload the workspace
          if(endHour<oldEndHour){
                AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(OVERWRITE_TITLE), props.getProperty(OVERWRITE_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        selection = yesNoDialog.getSelection();
            
              if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
          workspace.officeHoursGridPane.getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
               if(dataComponent.getOfficeHours().containsKey(key)){
                   dataComponent.setCellValue(key, (String)saveCellValues.get(key));
               }
           }
              j.addTransaction(new changeEndTime_Transaction(app, dataComponent, workspace, saveCellValues,endHour,oldEndHour));
              }
              else{
                  workspace.end.getSelectionModel().select(oldEndHour-1);
              }
          }
          else{
              workspace.officeHoursGridPane.getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
               if(dataComponent.getOfficeHours().containsKey(key)){
                   dataComponent.setCellValue(key, (String)saveCellValues.get(key));
               }
           }
              j.addTransaction(new changeEndTime_Transaction(app, dataComponent, workspace, saveCellValues,endHour,oldEndHour));
          }
       }
      
    }
    public void handleChangeStartTime(boolean go){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        int newStartHours=  Integer.parseInt((String)workspace.start.getSelectionModel().getSelectedItem());
            TAData dataComponent = (TAData)app.getDataComponent();
            int oldStartHour = dataComponent.getStartHour();
            
            // Save all the Values from the panes
             for (Pane p : workspace.officeHoursGridTACellPanes.values()) {
             
             String cellKey = p.getId();
              
             this.saveCellValues.put(cellKey, dataComponent.getCellValue(cellKey));     
             
              
             }
             if(!go)
             j.addTransaction(new changeStartTime_Transaction(app,dataComponent,workspace,this.saveCellValues,newStartHours,oldStartHour, true));
             else{
             
             if(newStartHours>= dataComponent.getEndHour()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(END_ERROR_TITLE), props.getProperty(END_ERROR_MESSAGE));
              workspace.start.getSelectionModel().select(oldStartHour-1);
       }
       else{
       String selection ="";
        
        if(newStartHours> oldStartHour){
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(OVERWRITE_TITLE), props.getProperty(OVERWRITE_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        selection = yesNoDialog.getSelection();
            
              if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
          dataComponent.setStartHour(Integer.parseInt((String)workspace.start.getSelectionModel().getSelectedItem()));
          //Reload the workspace
          workspace.officeHoursGridPane.getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
                

              int changeRow = newStartHours- oldStartHour;
              // this will update the cell key to the correct value we need it to be
             String newKey= dataComponent.setCellKeyToDifferent(key, changeRow);
               if(dataComponent.getOfficeHours().containsKey(key)){
                   // set the value of the cell key to the value that we need it to be (see setCellKeyToDifferent)
                   dataComponent.setCellValue(key, (String)saveCellValues.get(newKey));
               }
           }
              }
              else{
                  workspace.start.getSelectionModel().select(oldStartHour-1);
              } 
        
        
        
        
        
        
        
        }
        else{
          dataComponent.setStartHour(Integer.parseInt((String)workspace.start.getSelectionModel().getSelectedItem()));
          //Reload the workspace
          workspace.officeHoursGridPane.getChildren().clear();
           workspace.reloadOfficeHoursGrid(dataComponent);
           // Now restore the values we need
             for(String key: this.saveCellValues.keySet()){
                
    
              int changeRow = oldStartHour- newStartHours;
              // this will update the cell key to the correct value we need it to be
             String newKey= dataComponent.setCellKeyToDifferent(key, changeRow);
               if(dataComponent.getOfficeHours().containsKey(key)){
                   // set the value of the cell key to the value that we need it to be (see setCellKeyToDifferent)
                   dataComponent.setCellValue(newKey, (String)saveCellValues.get(key));
               }
           
              }
        
      
        }
        
    }
             
}
    }
}