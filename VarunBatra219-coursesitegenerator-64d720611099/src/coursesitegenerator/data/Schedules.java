/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Varun
 */
public class Schedules {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    private final StringProperty criteria;
    
    public Schedules(String type, String date, String time, String title, String topic, String link, String criteria){
         this.type = new SimpleStringProperty(type);
     this.date= new SimpleStringProperty(date);
     this.time= new SimpleStringProperty(time);
     this.title = new SimpleStringProperty(title);
     this.topic = new SimpleStringProperty(topic);
     this.link = new SimpleStringProperty(link);
     this.criteria= new SimpleStringProperty(criteria);
    }
    
    public String getType() {
        return type.get();
    }
    public String getDate() {
        return date.get();
    }
    public String getTime() {
        return time.get();
    }
    public String getTitle() {
        return title.get();
    }

    public String getTopic() {
       return topic.get();
    }
    public String getLink() {
       return link.get();
    }
    public String getCriteria() {
       return criteria.get();
    }
    
    public void setType(String sem) {
        type.set(sem);
    }
    public void setTime(String tim) {
        time.set(tim);
    }
    public void setTitle(String title) {
       this. title.set(title);
    }
     public void setTopic(String x) {
        topic.set(x);
    } 
     public void setLink(String x) {
        link.set(x);
    }
      public void setCriteria(String x) {
        criteria.set(x);
    }
    
    public void setDate(String x){
        date.set(x);
    }
    
    
    @Override
    public String toString() {
        return this.title.getValue();
    }

}
