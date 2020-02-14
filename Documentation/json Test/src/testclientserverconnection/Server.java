package testclientserverconnection;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import interfaces.*;



public class Server {
    ServerSocket serverSocket;
    Socket internalSocket;
    DataInputStream dis;
    PrintStream ps;
    Gson gson = new Gson();
    XOInterface xo;
    
    public Server()
    {
        try
        {
            serverSocket = new ServerSocket(5005);
            internalSocket = serverSocket.accept();
            dis = new DataInputStream(internalSocket.getInputStream());
            ps = new PrintStream(internalSocket.getOutputStream());
            String recivedMessgae = dis.readLine();
            xo = gson.fromJson(recivedMessgae, XOInterface.class);
            
            //////////////////////////////////
            System.out.println(xo.getPlayer().getPasswd());
            ps.println("Data Received");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        try 
        {
            ps.close();
            dis.close();
            internalSocket.close();
            serverSocket.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }
    
}
