/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursesitegenerator;

import coursesitegenerator.data.CourseInfoData;
import coursesitegenerator.workspace.CourseInfoWorkspace;

import djf.AppTemplate;
import java.util.Locale;
import static javafx.application.Application.launch;
import coursesitegenerator.data.TAData;
import coursesitegenerator.file.TAFiles;
import coursesitegenerator.style.TAStyle;
import coursesitegenerator.workspace.TAWorkspace;

/**
 *
 * @author Varun
 */
public class CourseSiteGeneratorApp extends AppTemplate {
     /**
     * This hook method must initialize all four components in the
     * proper order ensuring proper dependencies are respected, meaning
     * all proper objects are already constructed when they are needed
     * for use, since some may need others for initialization.
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL FOUR COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, SO BE CAREFUL OF THE ORDER
        dataComponent = new TAData(this);
    
        workspaceComponent = new TAWorkspace(this);
     
        fileComponent = new TAFiles(this);
        styleComponent = new TAStyle(this);
    }
    
    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
}