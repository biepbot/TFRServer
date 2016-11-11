package tfrserver;

import base.Room;
import base.SocketListenerRunnable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rowan
 */
public class ServerManager
{
    // The port of this server.
    private static final int BOUND_PORT = 5050;

    // The ServerSocket object.
    private static ServerSocket server;

    // Boolean indicating if the server should run.
    private static boolean serverIsStarted = false;

    // A list of all current rooms.
    private static List<Room> rooms = new ArrayList<>();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("[INFO] Application launched");
        // Check if the server exists.
        if (server == null)
        {
            // Create a new server.
            server = new ServerSocket(BOUND_PORT);
            serverIsStarted = true;
            System.out.println("[INFO] Server started");
        }

        // Keep looping as long as the server is allowed to run.
        while (serverIsStarted)
        {
            System.out.println("[INFO] Server listening for new connections...");
            // Create a new thread.
            Thread t = new Thread(new SocketListenerRunnable(server.accept()));

            // Start the created thread.
            t.start();
            System.out.println("[INFO] Connection established with client");
        }

        // Close the server when it's no longer allowed to run.
        System.out.println("[INFO] Server closing...");
        server.close();
    }

    /**
     * Finds a room by ID.
     *
     * @param roomID The ID of the room to be found.
     * @return The Room object. Returns an empty room when no rooms have been
     * found.
     */
    public static Room FindRoom(int roomID) throws IOException
    {
        // The room to be returned.
        Room ret = null;

        // Loop through all current rooms.
        for (Room r : rooms)
        {
            // Check if the ID matches.
            if (r.equals(roomID))
            {
                ret = r;
            }
        }

        // Create a new room if a room has not been found.
        if (ret == null)
        {
            ret = new Room(roomID);
        }

        // Return the found/created room.
        return ret;
    }

    /**
     * Ends this server.
     */
    public static void EndServer()
    {
        serverIsStarted = false;
    }
}
