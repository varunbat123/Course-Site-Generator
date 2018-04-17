/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.TAData;
import coursesitegenerator.workspace.TAWorkspace;
import coursesitegenerator.workspace.ScheduleWorkspace;
import javafx.scene.control.TableView;

import jtps.jTPS_Transaction;

/**
 *
 * @author Varun
 */
public class editSchedule_Transaction implements jTPS_Transaction {
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
        CourseSiteGeneratorApp app;
          Schedules s;
      
       TAWorkspace workSpace;
        TAData data;
        ScheduleWorkspace sWorkspace;
        ScheduleData sData;
public editSchedule_Transaction( String type, String title, String date, String topic, String link, String time, String criteria,  String oldType,String oldTitle, String oldDate, String oldTopic,String oldLink,String oldTime,String oldCriteria, int index,CourseSiteGeneratorApp app, Schedules s){
    this.type =type;
    this.date= date;
    this.topic = topic;
    this.link = link;
    this.title= title;
    this.time=time;
    this.criteria= criteria;
    this.oldType= oldType;
    this.oldTitle = oldTitle;
    this.app = app;
    this.oldTime= oldTime;
    this.oldTopic = oldTopic;
    this.oldDate= oldDate;
    this.oldLink= oldLink;
    this.oldCriteria= oldCriteria;
    this.index= index;
    this.s = s;
    workSpace = (TAWorkspace)app.getWorkspaceComponent();
    data = (TAData)app.getDataComponent();
    sWorkspace = workSpace.getScheduleWorkspace();
    sData = data.getScheduleData();
}
    @Override
    public void doTransaction() {
        TableView scheduleTable = sWorkspace.getTableView();
       s.setType(type);
        s.setTitle(title);
        s.setDate(date);
        s.setTopic(topic);
        s.setLink(link);
        s.setTime(time);
        s.setCriteria(criteria);
       scheduleTable.getItems().set(index, s);
    }

    @Override
    public void undoTransaction() {
       TableView scheduleTable = sWorkspace.getTableView();
       s.setType(oldType);
        s.setTitle(oldTitle);
        s.setDate(oldDate);
        s.setTopic(oldTopic);
        s.setLink(oldLink);
        s.setTime(oldTime);
        s.setCriteria(oldCriteria);
       scheduleTable.getItems().set(index, s);
    }
    
}
