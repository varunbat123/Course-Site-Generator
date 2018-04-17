/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.workspace;

import static coursesitegenerator.CourseInfoProp.BANNER_IMAGE_LABEL;
import static coursesitegenerator.CourseInfoProp.LEFT_FOOTER_IMAGE_LABEL;
import static coursesitegenerator.CourseInfoProp.RIGHT_FOOTER_IMAGE_LABEL;
import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.data.SiteData;
import coursesitegenerator.data.Sites;
import coursesitegenerator.data.TAData;

import static djf.settings.AppStartupConstants.PATH_WORK;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import properties_manager.PropertiesManager;

/**
 *
 * @author Varun
 */
public class CourseInfoController {
    CourseSiteGeneratorApp app;
    
    public CourseInfoController(CourseSiteGeneratorApp app){
        this.app = app;
        

    }

     public void handleChangeBannerImage() {
          TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         
         PropertiesManager props = PropertiesManager.getPropertiesManager();
      FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(BANNER_IMAGE_LABEL));
                
File dest= fc.showOpenDialog(app.getGUI().getWindow());
BufferedImage img = null;
try {
    img = ImageIO.read(dest);
  Image image =(SwingFXUtils.toFXImage(img,null));
    workSpace.getCourseWorkspace().bannerSelection.setImage( SwingFXUtils.toFXImage(img,null));
} catch (IOException e) {
    System.out.println("invalid File");
}
    }

    void handleChangeLeftImage() {
        TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         
         PropertiesManager props = PropertiesManager.getPropertiesManager();
      FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(LEFT_FOOTER_IMAGE_LABEL));
                
File dest= fc.showOpenDialog(app.getGUI().getWindow());
BufferedImage img = null;
try {
    img = ImageIO.read(dest);
  Image image =(SwingFXUtils.toFXImage(img,null));
    workSpace.getCourseWorkspace().leftSelection.setImage( SwingFXUtils.toFXImage(img,null));
} catch (IOException e) {
    System.out.println("invalid File");
}
    }

    void handleChangeRightImage() {
         TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
         
         PropertiesManager props = PropertiesManager.getPropertiesManager();
      FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(RIGHT_FOOTER_IMAGE_LABEL));
                
File dest= fc.showOpenDialog(app.getGUI().getWindow());
BufferedImage img = null;
try {
    img = ImageIO.read(dest);
  Image image =(SwingFXUtils.toFXImage(img,null));
    workSpace.getCourseWorkspace().rightSelection.setImage( SwingFXUtils.toFXImage(img,null));
} catch (IOException e) {
    System.out.println("invalid File");
}
    }

    public void handleSetTemplateDirectory() {
        TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
        CourseInfoWorkspace cWorkspace = workSpace.getCourseWorkspace();
    TAData data = (TAData) app.getDataComponent();
    SiteData sData = data.getSiteData();
      DirectoryChooser dc = new DirectoryChooser();
      File selectedFile = dc.showDialog(app.getGUI().getWindow());
   String [] files =selectedFile.list();
 cWorkspace.siteTemplateSelection.setText(selectedFile.getPath());
    for(String s: files){
       if(s.equals("index.html")){
    
             sData.addSite(true,"Home", "index.html","HomeBuilder.js");
    } 
              if(s.equals("syllabus.html")){
    
             sData.addSite(true,"Syllabus", "syllabus.html","SyllabusBuilder.js");
    }
                     if(s.equals("schedule.html")){
    
             sData.addSite(true,"Schedule", "schedule.html","ScheduleBuilder.js");
    }
                            if(s.equals("hws.html")){
    
             sData.addSite(true,"Hws", "hws.html","HWsBuilder.js");
    }
                                   if(s.equals("projects.html")){
    
             sData.addSite(true,"Projects", "projects.html","ProjectsBuilder.js");
    }
    }
     
       
    }

    public void handleChangeExportDir() {
       DirectoryChooser dc = new DirectoryChooser();
      File selectedFile = dc.showDialog(app.getGUI().getWindow());
      app.getGUI().setExportDir(selectedFile.getPath());
      TAWorkspace workSpace = (TAWorkspace)app.getWorkspaceComponent();
        CourseInfoWorkspace cWorkspace = workSpace.getCourseWorkspace();
        cWorkspace.exportDirSelection.setText(selectedFile.getPath());
    }
}
