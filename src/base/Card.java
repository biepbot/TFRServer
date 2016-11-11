package base;

/**
 *
 * @author Rowan
 */
public class Card
{
	// The cardType of the card.
    private CardType cardType;
	
	// The name/description of the card.
    private String name;
	
	/**
	 * Initializes a new instance of the Card class.
	 * @param type The cardType of the card.
	 * @param name The name/description of the card.
	 */
    public Card(CardType type, String name)
    {
        this.cardType = type;
        this.name = name;
    }

	/**
	 * Gets the cardType of this card.
	 * @return 
	 */
    public CardType getCardType()
    {
        return cardType;
    }
	
	/**
	 * Sets the cardType of this card.
	 * @param cardType The cardType.
	 */
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * Gets the name/description of this card.
	 * @return 
	 */
    public String getName()
    {
        return name;
    }
	
	/**
	 * Sets the name of this card.
	 * @param name The name.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
