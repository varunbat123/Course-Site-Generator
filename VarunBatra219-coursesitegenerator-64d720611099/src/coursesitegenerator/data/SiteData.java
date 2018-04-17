/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Varun
 */
public class SiteData {
     ObservableList<Sites> sites;
    
     public SiteData(){
      sites = FXCollections.observableArrayList();  
   } 
     
     
       public ObservableList getSites(){
    return this.sites;
       }
    public void addSite(boolean use,String nav, String file, String script){
      Sites s = new Sites (use,nav,file,script) ;
      sites.add(s);
    }

}
