/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Varun
 */
public class Team {
private final StringProperty name;
private final StringProperty color;
private final StringProperty text;
private final StringProperty link;  
private Color color1;
private Color color2;

public Team(String name, String color, String text, String link){
  this.name= new SimpleStringProperty(name);
     this.color = new SimpleStringProperty(color);
     this.text = new SimpleStringProperty(text); 
     this.link = new SimpleStringProperty(link);
   
}

public String getName() {
        return name.get();
    }
    public String getColor() {
        return color.get();
    }
    public String getText() {
        return text.get();
    }
    public String getLink() {
        return link.get();
    }

    public Color getColor1(){
        return this.color1;
    }
    public Color getColor2(){
        return this.color2;
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    public void setColor(String col) {
        color.set(col);
    }
    public void setText(String t) {
        text.set(t);
    }
    public void setLink(String l) {
   link.set(l);
    }
     public void setColor1(Color c) {
   this.color1 = c;
    }
      public void setColor2(Color c) {
   this.color2= c;
    }
}
