package base;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import tfrserver.ServerManager;

/**
 *
 * @author Rowan
 */
public class SocketListenerRunnable implements Runnable
{
    private final Gson gson;
    private final Socket socket;
    private Room r;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean doRun = false;

    /**
     * Initializes a new instance of the SocketListenerRunnable.
     *
     * @param accept The socket which accepts the listener.
     */
    public SocketListenerRunnable(Socket accept)
    {
        this.gson = new Gson();
        this.socket = accept;
        doRun = true;
    }

    /**
     * Stops this runnable.
     */
    public void Stop()
    {
        doRun = false;
    }

    public int readInt() throws IOException
    {
        byte[] rno = new byte[4];
        in.read(rno);
        return byteArrayToInt(rno);
    }

    public static int byteArrayToInt(byte[] b)
    {
        return b[0] & 0xFF
               | (b[1] & 0xFF) << 8
               | (b[2] & 0xFF) << 16
               | (b[3] & 0xFF) << 24;
    }

    @Override
    public void run()
    {
        try
        {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            //Get room
            int roomid = readInt();
            r = ServerManager.FindRoom(roomid);
            User u = new User(this, r);
            r.AcceptUser(u);
            System.out.println("[INFO] User connected to room " + roomid);

            while (doRun)
            {
                int type = readInt();
                int roomId;

                switch (type)
                {
                    case 1:
                        // Stuur hoeveelheid kaarten.
                        roomId = readInt();
                        int cardSize = readInt();

                        // Stuur de gebruiker kaarten.
                        SendCards(this.r.getHandler().ReadNewCards(cardSize));
                        break;
                    case 2:
                        // Stuur nieuwe task dmv ID en ronde.
                        roomId = readInt();
                        int round = readInt();

                        SendTask(this.r.getHandler().ReadNewTask(r.getUserCount()));
                        break;
                    case 3:
                        // Vote op kaartset.

                        break;
                    case 4:
                        // Start game.

                        break;
                    default:
                        // Explode.
                        break;
                }
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        try
        {
            Close();
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Sends a given task.
     *
     * @param task The task to send.
     */
    public void SendTask(Card task)
    {
        String jsonString = this.gson.toJson(task);

        try
        {
            sendIntAsFloat(2);

            System.out.println("[INFO] Sending the following:");
            System.out.println(jsonString);
            System.out.println(jsonString.getBytes("UTF-8").length);
            
            sendIntAsFloat(jsonString.getBytes("UTF-8").length);

            sendString(jsonString);
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Sends a list of cards.
     *
     * @param cards The list of cards to send.
     */
    public void SendCards(List<Card> cards)
    {
        String jsonString = this.gson.toJson(new CardHolder(cards));

        try
        {
            sendIntAsFloat(1);

            System.out.println("[INFO] Sending the following:");
            System.out.println(jsonString);
            System.out.println(jsonString.getBytes("UTF-8").length);

            sendIntAsFloat((jsonString.getBytes("UTF-8").length));

            sendString(jsonString);
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    private void sendString(String toSend) throws IOException
    {
        this.out.write(toSend.getBytes("UTF-8"));
        this.out.flush();
    }

    private void sendIntAsFloat(int toSend) throws IOException
    {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(toSend);
        byte[] bytes = buffer.array();
        System.out.println(Arrays.toString(bytes));
        this.out.write(bytes);
        this.out.flush();
    }

    /**
     * Closes this runnable.
     *
     * @throws IOException Thows an IOException when this method can't close
     * this runnable.
     */
    private void Close() throws IOException
    {
        out.close();
        in.close();
        socket.close();
    }
}
