package coursesitegenerator.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 * @author Varun Batra
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name;
    private final StringProperty email;
    /**
     * Constructor initializes the TA name
     */
    public TeachingAssistant(String initName, String initEmail) {
        name = new SimpleStringProperty(initName);
          email = new SimpleStringProperty(initEmail);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }
    public String getEmail() {
        return email.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }
    public void setEmail(String initName) {
        email.set(initName);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}