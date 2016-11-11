package base;

/**
 *
 * @author Rowan
 */
public class User
{
    private final Runnable connection;
    private final Room room;
	
	/**
	 * Initializes a new instance of the User class.
	 * @param conn A connection.
	 * @param r A room.
	 */
    public User(Runnable conn, Room r)
    {
        this.connection = conn;
        this.room = r;
    }
}
