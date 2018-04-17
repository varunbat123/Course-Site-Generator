/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator.file;

import coursesitegenerator.CourseSiteGeneratorApp;
import djf.components.AppDataComponent;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Varun
 */
public class TAFilesTest {
    
    public TAFilesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadData method, of class TAFiles.
     */
   

    /**
     * Test of loadData2 method, of class TAFiles.
     */
    @Test
    public void testLoadData2() throws Exception {
        InputStream is = new FileInputStream("C:/Users/Varun/documents/CSE 219/CourseSiteGenerator/work/nop.json");
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
        CourseSiteGeneratorApp app = new CourseSiteGeneratorApp();
       boolean x= json.containsKey("teams");
      
        assertEquals(true,x );
        assertEquals(true,json.containsKey("recitations") );
        assertEquals(false,json.containsKey("") );
        assertEquals(true,json.containsKey("students") );
        assertEquals(true,json.containsKey("schedules") );
        assertEquals(true,json.containsKey("officeHours") );
        assertEquals(true,json.containsKey("courses") );
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of saveData method, of class TAFiles.
     */
   
    /**
     * Test of importData method, of class TAFiles.
     */
    
    
}
