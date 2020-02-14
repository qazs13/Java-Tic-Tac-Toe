package Clent;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import interfaces.*;


public class Client {
    Socket clientSocket;
    DataInputStream dis;
    PrintStream ps;
    Player player = new Player("amrwsk", "amrwsk13", "amr", "walid");
    XOInterface xo = new XOInterface("createPlayer", player);
    Gson gson = new Gson();
    public Client()
    {
        String s = gson.toJson(xo);
        try
        {
            clientSocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            
            
            //////////////////////////////////
            ps.println(s);
            String replayedMessage = dis.readLine();
            System.out.println(replayedMessage);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            ps.close();
            dis.close();
            clientSocket.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}
