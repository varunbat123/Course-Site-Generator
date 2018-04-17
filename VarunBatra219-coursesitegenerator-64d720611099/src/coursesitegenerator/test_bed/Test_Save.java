/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.test_bed;

import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.data.CourseInfoData;
import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.Team;
import coursesitegenerator.file.TAFiles;
import djf.components.AppDataComponent;
import java.io.IOException;

/**
 *
 * @author Varun
 */
public class Test_Save  {
   AppDataComponent data;
   String filePath;
    public Test_Save(AppDataComponent data, String filePath) throws IOException{
    this.data = data;
    this.filePath = filePath;
    }
    
   public void save() throws IOException{
       
    TAData dataManager = (TAData)data;
      CourseInfoData courseData= (CourseInfoData) dataManager.getCourseData();
       ScheduleData scheduleData= (ScheduleData) dataManager.getScheduleData();
       scheduleData.addSchedule("holiday", "4/17/15", "4", "Schedule ex", " Example", "ex", "ex");
       ProjectData projectData = (ProjectData) dataManager.getProjectData();
       projectData.addTeam("exampleTeam", "blue", "red", "link");
       projectData.addStudent("Varun", "Batra", "exampleTeam", "Leader");
       RecitationData recitationData = (RecitationData) dataManager.getRecitationData();
       recitationData.addRecitation("01", "Mckduggal","1", "jargoose", "varun", "dog");
      courseData.addCourse("CSE", "Spring", "219", "2017");
    
        }  
}
