package base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rowan
 */
public class Room
{
    // A set amount of cards to be drawn for each type of card.
    public static final int STARTING_PACK = 3;

    // The ID of this room.
    private int id;

    // A CardHandler instance.
    private CardHandler cardHandler;

    // A list of connected users.
    private List<User> connectedUsers = new ArrayList<>();

    /**
     * Initializes a new instance of the Room class.
     *
     * @param roomid The ID of this room.
     * @throws java.io.IOException
     */
    public Room(int roomid) throws IOException
    {
        // Sets the ID of this room.
        id = roomid;
        cardHandler = new CardHandler();
    }

    /**
     * Accepts a user in this room.
     *
     * @param u The user to accept.
     */
    public void AcceptUser(User u)
    {
        // Add the user to the list of connected users.
        connectedUsers.add(u);
    }

    /**
     * Gets the amount of connected users.
     *
     * @return The amount of connected users.
     */
    public int getUserCount()
    {
        return connectedUsers.size();
    }

    /**
     * Gets the CardHandler instance.
     *
     * @return The CardHandler instance.
     */
    public CardHandler getHandler()
    {
        return cardHandler;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 11 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if ((getClass() != obj.getClass()) || !(obj instanceof Integer))
        {
            return false;
        }

        if (obj instanceof Integer)
        {
            if (this.id != (Integer) obj)
            {
                return false;
            }
        }
        else
        {
            final Room other = (Room) obj;
            if (this.id != other.id)
            {
                return false;
            }
        }
        return true;
    }

}
