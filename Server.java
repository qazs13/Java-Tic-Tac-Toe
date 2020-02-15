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
    XOInterface xoPlayer;
    Database db = new Database();
    static HashMap<ServerHandler,String> map=new HashMap<>();
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
                input=new DataInputStream(playerSocket.getInputStream());
                output=new PrintStream(playerSocket.getOutputStream());
                start();
                 } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        @Override
        public void run(){
        Gson incomeObjectFromPlayer = new Gson(); 
            while(true){
                try {
                    String message = input.readLine();
                    System.out.println(message);
                    xoPlayer = incomeObjectFromPlayer.fromJson(message, XOInterface.class);
                    System.out.println(xoPlayer);
                    /*
                    if(action_name[0].startsWith("login")){
                        //PlayerLoginCheck(xo)
                        PlayerLoginCheck(action_name); 
                    }*/
                    if(xoPlayer.getTypeOfOpearation().equals("Register")){
                          PlayerRegister(xoPlayer);
                    }
                    /*
                    if(action_name[0].startsWith("send")){
                            String str=action_name[2];
                            sendMsgToDesiredInternalSocket(str);          
                    }
*/
//                    sendMsgToAllInternalSocket(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
 
        void PlayerLoginCheck(String[] action_name){
                            for(int i=1;i<action_name.length;i++)
                            {
                                    System.out.println(action_name[i]);
                            }
                            //DataBase =db = new DataBase ();
                            //db.checkLogin(xo)
               Hashmapper(action_name);
        } 
        
        void PlayerRegister(XOInterface xoPlayer){
            if (db.checkSignUp(xoPlayer))
            {
                System.out.println("Player is Created ?"+db.createplayer(xoPlayer));
            }
            else
            {
                System.out.println("error");
            }
        }
        
       void Hashmapper(String[] action_name){
                    map.put(this,"yarab");
                    map.replace(this,"yarab", action_name[1]);
                    System.out.println(map);
        }         
        
        void sendMsgToDesiredInternalSocket(String msg){
            ServerHandler key = null;
            for(Map.Entry kv: map.entrySet()){
                    if (kv.getValue().equals("hakim")) {
                       key = (ServerHandler) kv.getKey();
                        System.out.println(key.toString());                             
                    }
            }
            key.output.println(msg);
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
