/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Varun
 */
public class Sites {
    private final BooleanProperty use;
   private final StringProperty nav;
  private final StringProperty file;
private final StringProperty script; 

public Sites(Boolean use,String nav, String file, String script){
    this.use = new SimpleBooleanProperty(use);
    this.nav = new SimpleStringProperty(nav);
   this.file = new SimpleStringProperty(file);
   this.script = new SimpleStringProperty(script);
    
}
public boolean getUse(){
    return this.use.get();
}
public String getNav(){
    return this.nav.get();
}
public String getFile(){
    return this.file.get();
}
public String getScript(){
    return this.script.get();
}





}
