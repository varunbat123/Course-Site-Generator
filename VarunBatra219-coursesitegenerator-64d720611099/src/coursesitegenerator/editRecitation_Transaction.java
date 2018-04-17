/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.Recitations;
import coursesitegenerator.data.TAData;
import coursesitegenerator.workspace.RecitationWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class editRecitation_Transaction implements jTPS_Transaction {
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
        jTPS j;
        CourseSiteGeneratorApp app;
        Recitations r;
      
        TAWorkspace workSpace;
        TAData data;
        RecitationWorkspace rWorkspace;
        RecitationData rData;
        public editRecitation_Transaction(CourseSiteGeneratorApp app, Recitations r, int index, String oldSupervisor1, String oldSupervisor2, String supervisor1, String supervisor2, String oldDate, String date, String oldInstructor, String instructor, String oldLocation, String location, String oldSection, String section){
            this.app = app;
            data = (TAData)app.getDataComponent();
       workSpace = (TAWorkspace)app.getWorkspaceComponent();
       rData = data.getRecitationData();
       rWorkspace = workSpace.getRecitationWorkspace();
            this.r=r;
          
            this.date= date;
            this.oldDate= oldDate;
            this.index= index;
            this.oldSection = oldSection;
            this.section= section;
            this.oldSupervisor1= oldSupervisor1;
            this.supervisor1= supervisor1;
            this.oldSupervisor2 = oldSupervisor2;
            this.supervisor2= supervisor2;
            this.oldLocation = oldLocation;
            this.location = location;
            this.instructor = instructor;
            this.oldInstructor = oldInstructor;
            
            
        }
    @Override
    public void doTransaction() {
        TableView recitationTable = rWorkspace.getTableView();
      r.setInstructor(instructor);
        r.setSection(section);
        r.setDate(date);
        r.setLocation(location);
        r.setSupervisor1(supervisor1);
        r.setSupervisor2(supervisor2);
       recitationTable.getItems().set(index, r);
       
    }

    @Override
    public void undoTransaction() {
   TableView recitationTable = rWorkspace.getTableView();
     r.setInstructor(oldInstructor);
        r.setSection(oldSection);
        r.setDate(oldDate);
        r.setLocation(oldLocation);
        r.setSupervisor1(oldSupervisor1);
        r.setSupervisor2(oldSupervisor2);
       recitationTable.getItems().set(index, r);
    }
    
}
