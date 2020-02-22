package server;

import com.google.gson.Gson;
import interfaces.*;
import database.Database;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    ServerSocket server_socket;
    Database db = new Database();
    String message = null;
    static HashMap<ServerHandler,String> map=new HashMap<>();
    Gson incomeObjectFromPlayer = null;
    public void runServer(){
        try {
            System.err.println("Server is Opened Succesfully");
            server_socket = new ServerSocket(5000);
            while(true){
                Socket internal_socket=server_socket.accept();
                System.out.println("New Player Is Here");
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
                    this.playerSocket = socket;
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
                    System.err.println("New Message From Player");
                    XOInterface xoPlayer = incomeObjectFromPlayer.fromJson(message, XOInterface.class);
                    if(xoPlayer.getTypeOfOpearation().equals(Messages.LOGIN))
                    {
                        PlayerLoginCheck(xoPlayer);                        
                    }
                    
                    else if(xoPlayer.getTypeOfOpearation().equals(Messages.REGISTER))
                    {
                        PlayerRegister(xoPlayer);
                    }
                    
                    else
                    {
                        if(xoPlayer.getTypeOfOpearation().equals(Messages.PLAYING_SINGLE_MODE))
                        {
                            playingSingleMode(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.SINGLE_MODE_FINISHED))
                        {
                            updatePlayerScoreOffline(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.GET_PLAYERS))
                        {
                            getAllPlayers(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.INVITE))
                        {
                            invitePlayer(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.INVITATION_ACCEPTED))
                        {
                            createGame(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.INVITATION_REJECTED))
                        {
                            rejectingInvitation(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.PLAY_MOVE))
                        {
                            makeMove(xoPlayer);
                        }   
                                               
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.MULTI_MODE_FINISHED))
                        {
                            endGame(xoPlayer);
                        }
                        
                        else if(xoPlayer.getTypeOfOpearation().equals(Messages.RESUME))
                        {
                          retrivingGameList(xoPlayer) ;
                        }                        
                    }
                    
                } catch (IOException ex) 
                {
                    try {
                        this.input.close();
                        this.output.close();
                        this.playerSocket.close();
                        this.stop();                   
                        break;
                    } catch (IOException ex1) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
 
        void PlayerLoginCheck(XOInterface xoPlayer){
            if (db.checkLogIn(xoPlayer))
            {
                Hashmapper(xoPlayer);
                xoPlayer = db.makePlayerOnline(xoPlayer);
                xoPlayer.setTypeOfOpearation(Messages.NEW_PLAYER_LOGGED_IN);
                xoPlayer.getPlayer().setPasswd(null);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                System.err.println(message);
                this.output.println(message);
                xoPlayer.setTypeOfOpearation(Messages.NEW_PLAYER_LOGGEDIN_POP);
                sendMsgToAllInternalSocket(xoPlayer);
            }
            else
            {
                xoPlayer.setOpearationResult(false);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);
            }
        } 
        
        void PlayerRegister(XOInterface xoPlayer){
            if (db.checkSignUp(xoPlayer))
            {
                XOInterface xoPlayerRecived = new XOInterface();
                xoPlayerRecived.setTypeOfOpearation(Messages.SIGN_UP_ACCEPTED);
                boolean flag = db.createplayer(xoPlayer);
                xoPlayerRecived.setOpearationResult(flag);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayerRecived);
                this.output.println(message);              
            }
            else
            {   
                System.out.println("User Name is Existed");
                xoPlayer.setTypeOfOpearation(Messages.SIGN_UP_REJECTED);
                xoPlayer.setOpearationResult(false);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);                
            }
        }
 
       void playingSingleMode(XOInterface xoPlayer){
            System.out.println("Player Is Playing Single Mood");
            xoPlayer.setOpearationResult(db.makePlayerIsPlaying(xoPlayer));
            incomeObjectFromPlayer = new Gson();
            message = incomeObjectFromPlayer.toJson(xoPlayer);
            this.output.println(message);
       }
               
       void updatePlayerScoreOffline(XOInterface xoPlayer){
            if(db.updateScoreOffline(xoPlayer))
            {   
                xoPlayer = db.getScore(xoPlayer);
                xoPlayer.setTypeOfOpearation(Messages.SINGLE_MODE_SCORE_UPDATED);                
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);
            }
            else
            {
                xoPlayer.setOpearationResult(false);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);     
            }
        }

       void getAllPlayers(XOInterface xoPlayer)
       {
           xoPlayer = db.retriveAllPlayers();
           xoPlayer.setTypeOfOpearation(Messages.RETREVING_PLAYERS_LIST);                
           incomeObjectFromPlayer = new Gson();
           message = incomeObjectFromPlayer.toJson(xoPlayer);
           this.output.println(message);
       }
       
       void createGame(XOInterface xoPlayer)
       {
           xoPlayer = db.createGame(xoPlayer);
           xoPlayer.setTypeOfOpearation(Messages.INVITATION_ACCEPTED);
           incomeObjectFromPlayer = new Gson();
           message = incomeObjectFromPlayer.toJson(xoPlayer);             
           this.output.println(message);
           String home = xoPlayer.getGameLog().getOpponentPlayer();
           xoPlayer.getGameLog().setOpponentPlayer(xoPlayer.getGameLog().getHomePlayer());
           xoPlayer.getGameLog().setHomePlayer(home);
           sendMsgToDesiredInternalSocket(xoPlayer);      
       }
       
       void invitePlayer(XOInterface xoPlayer)
       {
           xoPlayer.setTypeOfOpearation(Messages.RECEIVING_INVITATION);
           sendMsgToDesiredInternalSocket(xoPlayer);  
       }
       
       void rejectingInvitation(XOInterface xoPlayer)
       {
           xoPlayer.getGameLog().setOpponentPlayer(xoPlayer.getGameLog().getHomePlayer());
           sendMsgToDesiredInternalSocket(xoPlayer);
       }
       
        void makeMove(XOInterface xoPlayer)
        {
           xoPlayer = db.setGameMove(xoPlayer);
           xoPlayer.setTypeOfOpearation(Messages.RECEIVING_MOVE);
           sendMsgToDesiredInternalSocket(xoPlayer);
       }

       void endGame(XOInterface xoPlayer){
            if(db.endGame(xoPlayer))
            {   
                db.updateScoreOnline(xoPlayer);
                xoPlayer = db.getScore(xoPlayer); //getScore function in data base need to be created 
                xoPlayer.setTypeOfOpearation(Messages.GAME_ENDED);                
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);
            }
            
            else
            {
                xoPlayer.setOpearationResult(false);
                incomeObjectFromPlayer = new Gson();
                message = incomeObjectFromPlayer.toJson(xoPlayer);
                this.output.println(message);     
            }           
       }
       
        void retrivingGameList(XOInterface xoPlayer){
           xoPlayer = db.getSavedGame(xoPlayer);
           xoPlayer.setTypeOfOpearation(Messages.RETRIVEMOVES);
           incomeObjectFromPlayer = new Gson();
           message = incomeObjectFromPlayer.toJson(xoPlayer);             
           this.output.println(message);
           sendMsgToDesiredInternalSocket(xoPlayer);      
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
            System.out.println(message);
            key.output.println(message);
        }
           
        void sendMsgToAllInternalSocket(XOInterface xoPlayer)
        {
            ServerHandler key = null;
            incomeObjectFromPlayer = new Gson();
            message = incomeObjectFromPlayer.toJson(xoPlayer);
            for(Map.Entry kv: map.entrySet()){
                  
                System.out.println(kv);
                key = (ServerHandler) kv.getKey();
                key.output.println(message);
            }
        }
    }
        public void stopServer()
        {
            try {
                System.out.println("Stopped Server");
                server_socket.close();
            } catch (IOException ex) {
                System.out.println("Error in Shutdown The Server");
            }
        }    
}
