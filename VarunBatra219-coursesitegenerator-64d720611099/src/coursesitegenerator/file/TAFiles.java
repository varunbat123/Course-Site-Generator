package coursesitegenerator.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import coursesitegenerator.CourseSiteGeneratorApp;
import coursesitegenerator.data.CourseInfoData;
import coursesitegenerator.data.Courses;
import coursesitegenerator.data.ProjectData;
import coursesitegenerator.data.RecitationData;
import coursesitegenerator.data.Recitations;
import coursesitegenerator.data.ScheduleData;
import coursesitegenerator.data.Schedules;
import coursesitegenerator.data.Students;
import coursesitegenerator.data.TAData;
import coursesitegenerator.data.TeachingAssistant;
import coursesitegenerator.data.Team;
import coursesitegenerator.test_bed.Test_Save;
import coursesitegenerator.workspace.TAWorkspace;

/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Richard McKenna
 */
public class TAFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    CourseSiteGeneratorApp app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
      static final String JSON_EMAIL = "email";
      static final String JSON_SUBJECT = "subject";
      static final String JSON_YEAR = "year";
      static final String JSON_SEMESTER = "semester";
      static final String JSON_NUMBER = "number";
      static final String JSON_COURSES = "courses";
      static final String JSON_RECITATIONS = "recitations";
      static final String JSON_SCHEDULES = "schedules";
      
      static final String JSON_SECTION = "section";
      static final String JSON_INSTRUCTOR = "instructor";
      static final String JSON_LOCATION = "location";
      static final String JSON_SUPERVISOR1 = "supervisor1";
      static final String JSON_SUPERVISOR2 = "supervisor2";
      static final String JSON_TYPE = "type";
      static final String JSON_DATE = "date";
      static final String JSON_SCHEDULE_TIME = "time";
      static final String JSON_TITLE = "title";
      static final String JSON_TOPIC = "topic";
        static final String JSON_LINK = "link";
      static final String JSON_CRITERIA= "criteria";
       static final String JSON_TEAM_NAME = "team";
      static final String JSON_TEAM_COLOR= "color";
      static final String JSON_TEAM_TEXT_COLOR = "text color";
      static final String JSON_TEAM_LINK = "link";
      static final String JSON_STUDENT_FIRST = "first name";
      static final String JSON_STUDENT_LAST = "last name";
      static final String JSON_STUDENT_TEAM = "team";
        static final String JSON_STUDENT_ROLE = "role";
           static final String JSON_TEAMS = "teams";
           static final String JSON_STUDENTS = "students";
           
      
      
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    
    public TAFiles(CourseSiteGeneratorApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	TAData dataManager = (TAData)data;
         CourseInfoData courseData= (CourseInfoData) dataManager.getCourseData();
       ScheduleData scheduleData= (ScheduleData) dataManager.getScheduleData();
       ProjectData projectData = (ProjectData) dataManager.getProjectData();
       RecitationData recitationData = (RecitationData) dataManager.getRecitationData();

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
         app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name,email);
        }
         JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_SECTION);
            String instructor  = jsonRecitation.getString(JSON_INSTRUCTOR);
             String day  = jsonRecitation.getString(JSON_DATE);
            String location  = jsonRecitation.getString(JSON_LOCATION);
            
            String supervisor1  = jsonRecitation.getString(JSON_SUPERVISOR1);
            String supervisor2  = jsonRecitation.getString(JSON_SUPERVISOR2);
           recitationData.addRecitation(section, instructor,day, location, supervisor1,supervisor2);
          
        }
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULES);
        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            String type = jsonSchedule.getString(JSON_TYPE);
            String time = jsonSchedule.getString(JSON_TIME);
           String date = jsonSchedule.getString(JSON_DATE);
            String title = jsonSchedule.getString(JSON_TITLE);
            String topic= jsonSchedule.getString(JSON_TOPIC);
            String link= jsonSchedule.getString(JSON_LINK);
            String criteria= jsonSchedule.getString(JSON_CRITERIA);
           scheduleData.addSchedule(type, date, time, title,topic,link,criteria);
           
           
           
          
        }
        
         JsonArray jsonCourseArray = json.getJsonArray(JSON_COURSES);
        for (int i = 0; i < jsonCourseArray.size(); i++) {
            JsonObject jsonCourse = jsonCourseArray.getJsonObject(i);
            String subject = jsonCourse.getString(JSON_SUBJECT);
            String semester = jsonCourse.getString(JSON_SEMESTER);
           String number = jsonCourse.getString(JSON_NUMBER);
            String year = jsonCourse.getString(JSON_YEAR);
         
            
           courseData.addCourse(subject, semester, number,year);
          
        }
        JsonArray jsonProjectArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonProjectArray.size(); i++) {
            JsonObject jsonProject = jsonProjectArray.getJsonObject(i);
            String teamName = jsonProject.getString(JSON_TEAM_NAME);
            String teamColor = jsonProject.getString(JSON_TEAM_COLOR);
           String teamText = jsonProject.getString(JSON_TEAM_TEXT_COLOR);
            String teamLink = jsonProject.getString(JSON_TEAM_LINK);
           
         
            
           projectData.addTeam(teamName, teamColor, teamText,teamLink);
        }
          JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
            JsonObject jsonProject = jsonStudentArray.getJsonObject(i);
            String studentFirst = jsonProject.getString(JSON_STUDENT_FIRST);
            String studentLast = jsonProject.getString(JSON_STUDENT_LAST);
           String studentTeam = jsonProject.getString(JSON_STUDENT_TEAM);
            String studentRole = jsonProject.getString(JSON_STUDENT_ROLE);
           
         
            
            projectData.addStudent(studentFirst,studentLast,studentTeam,studentRole);
        }
        
        
          
        
        
        

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
    }
    
    public boolean loadData2(String filePath, String key) throws IOException {
	// CLEAR THE OLD DATA OUT
	

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	
        return json.containsKey(key);
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        
       TAData dataManager = (TAData)data;
      CourseInfoData courseData= (CourseInfoData) dataManager.getCourseData();
       ScheduleData scheduleData= (ScheduleData) dataManager.getScheduleData();
       ProjectData projectData = (ProjectData) dataManager.getProjectData();
      
       RecitationData recitationData = (RecitationData) dataManager.getRecitationData();
       
       TAFiles fileController = new TAFiles(app);
    
	// GET THE DATA
	
      
       
       JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
	ObservableList<Team> teams = projectData.getTeams();
        
	for (Team t : teams) {	    
	    JsonObject jTeam = Json.createObjectBuilder()
		    .add(JSON_TEAM_NAME, t.getName())
                    .add(JSON_TEAM_COLOR,t.getColor())
                    .add(JSON_TEAM_TEXT_COLOR, t.getText())
                    .add(JSON_TEAM_LINK, t.getLink())
                    
                    .build();
        
	    teamArrayBuilder.add(jTeam);
	}
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Students> students = projectData.getStudents();
	for (Students s : students) {	    
	    JsonObject jStudent = Json.createObjectBuilder()
		    .add(JSON_STUDENT_FIRST, s.getFirstName())
                    .add(JSON_STUDENT_LAST,s.getLastName())
                    .add(JSON_STUDENT_TEAM, s.getTeam())
                    .add(JSON_STUDENT_ROLE, s.getRole())
                    
                    .build();
        
	    studentArrayBuilder.add(jStudent);
	}
        
	JsonArray teamsArray = teamArrayBuilder.build();
       JsonArray studentsArray = studentArrayBuilder.build();
       
       JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
	ObservableList<Schedules> schedules = scheduleData.getSchedules();
	for (Schedules s : schedules) {	    
	    JsonObject jSchedule = Json.createObjectBuilder()
		    .add(JSON_TYPE, s.getType())
                    .add(JSON_DATE,s.getDate())
                    .add(JSON_TIME, s.getTime())
                    .add(JSON_TITLE, s.getTitle())
                     .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_LINK, s.getLink())
                    .add(JSON_CRITERIA, s.getCriteria())
                    .build();
        
	    scheduleArrayBuilder.add(jSchedule);
	}
	JsonArray schedulessArray = scheduleArrayBuilder.build();

       
       
       
       JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitations> recitations = recitationData.getRecitations();
	for (Recitations r : recitations) {	    
	    JsonObject jRecitation = Json.createObjectBuilder()
		    .add(JSON_SECTION, r.getSection())
                    .add(JSON_INSTRUCTOR,r.getInstructor())
                    .add(JSON_DATE, r.getDate())
                    .add(JSON_LOCATION, r.getLocation())
                    .add(JSON_SUPERVISOR1, r.getSupervisor1())
                     .add(JSON_SUPERVISOR2, r.getSupervisor2())
                    .build();
        
	    recitationArrayBuilder.add(jRecitation);
	}
	JsonArray recitationsArray = recitationArrayBuilder.build();
        
       
       
        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
	ObservableList<Courses> courses = courseData.getCourse();
	for (Courses c : courses) {	    
	    JsonObject jCourse = Json.createObjectBuilder()
		    .add(JSON_SUBJECT, c.getSubject())
                    .add(JSON_SEMESTER,c.getSemester())
                    .add(JSON_YEAR, c.getYear())
                    .add(JSON_NUMBER, c.getNumber())
                    
                    .build();
        
	    courseArrayBuilder.add(jCourse);
	}
	JsonArray coursesArray = courseArrayBuilder.build();
        
        
        
        
        
	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
        
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_COURSES, coursesArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_TEAMS, teamsArray)
                .add(JSON_STUDENTS, studentsArray)
                .add(JSON_SCHEDULES, schedulessArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
   
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}