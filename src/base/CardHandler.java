package base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Rowan
 */
public class CardHandler
{
	// A static Random object.
    private static Random r = new Random();
	
	// A list of drawn cards.
    private List<Card> drawnCards = new ArrayList<>();
	
	// A list of tool cards.
    private List<Card> toolCards = new ArrayList<>();
	
	// A list of character cards.
    private List<Card> characterCards = new ArrayList<>();
	
	// A list of disability cards.
    private List<Card> disabilityCards = new ArrayList<>();
	
	// A list of task cards.
    private List<Card> taskCards = new ArrayList<>();
	
	// The current round number.
    private int thisround;
	
	// The amount of people who are ready.
    private int readycount = 0;
    
	// The current task card.
    private Card currentTask;
    
	/**
	 * Initializes a new instance of the CardHandler class.
	 * @throws IOException Throws an IOException when something went wrong while initializing this class.
	 */
    public CardHandler() throws IOException
    {
		// Get a copy of all cards.
        List<Card> unusedCards = Reader.getInstance().getAllCards();
		
		// Loop through all cards in the cards array.
        for (Card d : unusedCards)
        {
			// Sort the cards depending on the type.
            CardType de = d.getCardType();
            switch (de)
            {
                case Character:
                    characterCards.add(d);
                    break;
                case Disability:
                    disabilityCards.add(d);
                    break;
                case Tool:
                    toolCards.add(d);
                    break;
                case Task:
                    taskCards.add(d);
                    break;
                default:
                    break;
            }
        }
    }
	
	/**
	 * Generates a new task.
	 * @param maxplayers The max amount of players.
	 * @return Returns a new Task card.
	 */
    public Card ReadNewTask(int maxplayers)
    {
        boolean newtask = false;
        //If not all players have pressed "next turn", yield the last Task
        if (readycount % maxplayers > 0)
        {
            thisround++;
        }
        else
        {
            //Else, generate a new one
            newtask = true;
        }

        if (currentTask == null)
        {
            newtask = true;
        }
        
        if (newtask)
        {
            Card rem = disabilityCards.get(r.nextInt(characterCards.size() - 1));

            taskCards.remove(rem);
            drawnCards.add(rem);
            currentTask = rem;
        }
        
        return currentTask;
    }
	
	/**
	 * Generates some new cards.
	 * @param amount The amount of cards to be generated.
	 * @return Returns a list of cards.
	 */
    public List<Card> ReadNewCards(int amount)
    {
        //TODO
        //Check if not too many cards are asked from the server
		
		// Create an array to store the cards.
        List<Card> removedCards = new ArrayList<>();
        
		// Loop through the amount of cards.
		for (int i = 0; i < amount; i++)
        {
			// Get a random character card.
            Card rem = characterCards.get(r.nextInt(characterCards.size() - 1));
			
			// Remove the card from the characters array, and add it to the removed cards.
            characterCards.remove(rem);
            removedCards.add(rem);
			
			// Get a disability card.
            rem = disabilityCards.get(r.nextInt(characterCards.size() - 1));

			// Remove the card from the disabilities array, and add it to the removed cards.
            disabilityCards.remove(rem);
            removedCards.add(rem);
			
			// Get a tool card.
            rem = toolCards.get(r.nextInt(characterCards.size() - 1));

			// Remove the card from the tools array, and add it to the removed cards.
            toolCards.remove(rem);
            removedCards.add(rem);
        }
        drawnCards.addAll(removedCards);
        return removedCards;
    }
}
