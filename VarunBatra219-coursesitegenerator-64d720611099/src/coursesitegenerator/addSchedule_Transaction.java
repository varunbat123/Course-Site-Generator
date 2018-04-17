/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.workspace.ScheduleWorkspace;
import coursesitegenerator.workspace.TAWorkspace;
import javafx.scene.control.TableView;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class addSchedule_Transaction implements jTPS_Transaction {
   CourseSiteGeneratorApp app;
    jTPS j;
 Schedules s;
  TAWorkspace workSpace;
  TAData data;
  String type;
  String topic;
  String date;
  String link;
  String criteria;
  String title;
  String time;
  int index; 
  ScheduleWorkspace sWorkspace;
  ScheduleData sData;
public addSchedule_Transaction(CourseSiteGeneratorApp app,Schedules s, int index){
    this.app = app;
    this.s=s;
    this.index = index;
    this.workSpace = (TAWorkspace) app.getWorkspaceComponent();
    this.data = (TAData) app.getDataComponent();
    this.sWorkspace= workSpace.getScheduleWorkspace();
    sData= data.getScheduleData();
    type = s.getType();
    topic = s.getTopic();
    date = s.getDate();
    link = s.getLink();
    criteria = s.getCriteria();
    title = s.getTitle();
    time = s.getTime();
}
    @Override
    public void doTransaction() {
        sData.addSchedule(type, date, time, title, topic, link, criteria);
    }

    @Override
    public void undoTransaction() {
         TableView scheduleTable = sWorkspace.getTableView();
       
           scheduleTable.getItems().remove(index);
    }
}
