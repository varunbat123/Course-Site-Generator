/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.TAData;
import coursesitegenerator.workspace.ScheduleWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class deleteSchedule_Transaction implements jTPS_Transaction {
CourseSiteGeneratorApp app;
 int index;
  
 Schedules s;
  
  TAWorkspace workSpace;
  TAData data;
 ScheduleWorkspace sWorkspace;
  ScheduleData sData;
  String type;
        String title;
        String date;
        String topic;
        String link;
        String time;
        String criteria;
      public deleteSchedule_Transaction(CourseSiteGeneratorApp app, int index, Schedules s){
          this.app = app;
          workSpace = (TAWorkspace) app.getWorkspaceComponent();
          data = (TAData)app.getDataComponent();    
          sWorkspace = workSpace.getScheduleWorkspace();
          sData = data.getScheduleData();
          this.index= index;
          this.s = s;
          this.type = s.getType();
          this.title = s.getTitle();
         date = s.getDate();
         topic = s.getTopic();
         link = s.getLink();
         time = s.getTime();
         criteria = s.getCriteria();
      }  
    @Override
    public void doTransaction() {
       TableView scheduleTable = sWorkspace.getTableView();
       scheduleTable.getItems().remove(index);
    }

    @Override
    public void undoTransaction() {
       sData.addSchedule(type, date, time, title, topic, link, criteria);
    }
    
}
