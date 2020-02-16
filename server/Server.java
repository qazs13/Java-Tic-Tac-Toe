package server;

import com.google.gson.Gson;
import interfaces.*;
import database.Database;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    ServerSocket server_socket;
    Database db = new Database();
    String message = null;
    static HashMap<ServerHandler,String> map=new HashMap<>();
    Gson incomeObjectFromPlayer = null;
    public Server(){
        try {
            server_socket=new ServerSocket(5000);
            while(true){
                Socket internal_socket=server_socket.accept();
                new ServerHandler(internal_socket);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class ServerHandler extends Thread
    {
        private DataInputStream input;
        private PrintStream output;
        private Socket playerSocket;

        public ServerHandler(Socket socket){
           
            try {
                this.playerSocket=socket;
                input = new DataInputStream(playerSocket.getInputStream());
                output = new PrintStream(playerSocket.getOutputStream());
                start();
                }
            catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        @Override
        public void run(){
            incomeObjectFromPlayer = new Gson();
            while(true){
                try {
                    message = input.readLine();
                    XOInterface xoPlayer = incomeObjectFromPlayer.fromJson(message, XOInterface.class);
                    
                    if(xoPlayer.getTypeOfOpearation().equals("login"))
                    {
                        PlayerLoginCheck(xoPlayer); 
                    }
                    else if(xoPlayer.getTypeOfOpearation().equals("register"))
                    {
                        PlayerRegister(xoPlayer);
                    }
                    else
                    {
                                   
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
 
        void PlayerLoginCheck(XOInterface xoPlayer){
            if (db.checkLogIn(xoPlayer))
            {
                Hashmapper(xoPlayer); 
                xoPlayer = db.makePlayerOnline(xoPlayer);
                xoPlayer.getGameLog().setOpponentPlayer(xoPlayer.getGameLog().getHomePlayer());
                sendMsgToDesiredInternalSocket(xoPlayer);
            }
            else
            {
                xoPlayer.setOpearationResult(false);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);
               // this.stop();
            }
        } 
        
        void PlayerRegister(XOInterface xoPlayer){
            if (db.checkSignUp(xoPlayer))
            {
                System.out.println("Player is Created ? "+db.createplayer(xoPlayer));
            }
            else
            {
                System.out.println("error");
            }
        }
        
       void Hashmapper(XOInterface xoPlayer){
            map.put(this,xoPlayer.getPlayer().getUserName());
            System.out.println(map);
        }         
        
        void sendMsgToDesiredInternalSocket(XOInterface xoPlayer){
            ServerHandler key = null;
            incomeObjectFromPlayer = new Gson();
            for(Map.Entry kv: map.entrySet()){
                    if (kv.getValue().equals(xoPlayer.getGameLog().getOpponentPlayer())) {
                        key = (ServerHandler) kv.getKey();
                        System.out.println(key.toString());                             
                    }
            }
            message = incomeObjectFromPlayer.toJson(xoPlayer);
            key.output.println(message);
        }
        
       
        void sendMsgToAllInternalSocket(String msg){
            for(Map.Entry kv: map.entrySet()){
                  System.out.println(kv);
            }
         }
    }
     
    public static void main(String[] args) {
       Server server=new Server();
    } 
    
}
