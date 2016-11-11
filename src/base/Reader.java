package base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rowan
 */
public class Reader
{
	// Path to a textfile containing all different tools.
    private static final String TOOLS_LOCATION = "tools.txt";
	
	// Path to a textfile containing all different characters.
    private static final String CHAR_LOCATION = "char.txt";
	
	// Path to a textfile containing all different disabilities.
    private static final String DIS_LOCATION = "dis.txt";
	
	// Path to a textfile containing all different tasks.
    private static final String TASK_LOCATION = "task.txt";
    
	// A list of all cards.
    private List<Card> allcards = new ArrayList<>();
    
	// A reader instance.
    private static Reader instance;
    
	/**
	 * Initializes a new instance of the Reader class.
	 */
    private Reader()
    {
        
    }
    
	/**
	 * Gets the Reader instance. Creates a new Reader instance if the instance is null.
	 * @return The Reader instance.
	 */
    public static Reader getInstance()
    {        
        return (instance == null ? (instance = new Reader()) : instance);
    }
    
	/**
	 * Gets a list of all cards.
	 * @return A list of all cards.
	 * @throws IOException Throws an IOException when it cannot return all cards.
	 */
    public List<Card> getAllCards() throws IOException
    {
        if (!allcards.isEmpty())
        {
            return allcards;
        }
        
        //Read from files
        List<String> characters = Files.readAllLines(new File(CHAR_LOCATION).toPath());
        List<String> tools = Files.readAllLines(new File(TOOLS_LOCATION).toPath());
        List<String> tak = Files.readAllLines(new File(TASK_LOCATION).toPath());
        List<String> dis = Files.readAllLines(new File(DIS_LOCATION).toPath());
        
        for (String s : characters)
        {
            allcards.add(new Card(CardType.Character, s));
        }
        for (String s : tools)
        {
            allcards.add(new Card(CardType.Tool, s));
        }
        for (String s : tak)
        {
            allcards.add(new Card(CardType.Task, s));
        }
        for (String s : dis)
        {
            allcards.add(new Card(CardType.Disability, s));
        }
        
        return allcards;
    }
}
