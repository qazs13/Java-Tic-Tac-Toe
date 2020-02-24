package tictactoegui;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import signin.*;
import interfaces.*;
import javafx.util.*;
import javafx.animation.PauseTransition;
import javafx.stage.StageStyle;
import online.*;
import invitationpopup.*;
import java.io.File;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import multiPlayers.MultiPlayerController;
import onlinepopup.onLinePopupController;

public class TicTacToeGui extends Application {
    DataInputStream dis;
    public static PrintStream ps;
    Socket mySocket;
    MultiPlayerController MI;
    @Override
    public void start(Stage stage) throws Exception {
        try{
            mySocket = new Socket("127.0.0.1", 5000);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            new Thread(()->{
                while (true){
                    try {
                        String recievedMsg = dis.readLine();
                        System.out.println(recievedMsg);
                        Gson g = new Gson();
                        XOInterface xoMsg;
                        xoMsg = g.fromJson(recievedMsg, XOInterface.class);
                        if(xoMsg.getTypeOfOpearation().equals(Messages.NEW_PLAYER_LOGGED_IN))
                        {
                            Platform.runLater(()->{
                                try {
                                    switchToSelectionScene(stage);
                                } catch (IOException ex) {
                                    System.err.println("coudn't switch");
                                    ex.printStackTrace();
                                }
                            });
                        }
                        else if (xoMsg.getTypeOfOpearation().equals(Messages.SIGN_UP_ACCEPTED))
                        {
                            System.err.println("Register here");
                            Platform.runLater(()->{
                            switchToLogIn(stage);
                            });                           
                        }
                        
                        else if (xoMsg.getTypeOfOpearation().equals(Messages.NEW_PLAYER_LOGGEDIN_POP))
                        {
                            switchToOnpopupscene(xoMsg);
                        }
                        
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.RECEIVING_INVITATION))
                        {
                           switchToInvitationpopupscene(xoMsg);
                        }
                          
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.RETREVING_PLAYERS_LIST))
                        {
                                Platform.runLater(()->{
                                try {
                                    switchToOnLineScene(stage,xoMsg);
                                } catch (IOException ex) {
                                    System.err.println("coudn't switch");
                                    ex.printStackTrace();
                                }
                            });              

                        }
                        
                        else if (xoMsg.getTypeOfOpearation().equals(Messages.INVITATION_ACCEPTED))
                        {
                            Platform.runLater(() -> {
                                switchToMultiPlayer(stage, xoMsg);
                            });
                        }
                        
                        else if (xoMsg.getTypeOfOpearation().equals(Messages.INVITATION_REJECTED))
                        {
                            SignInController.myTurn = false;
                        }
                        
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.PLAYING_SINGLE_MODE))
                        {
                            Platform.runLater(()->{
                                try 
                                {
                                    switchToSinglePlayerScene(stage);
                                } 
                                catch (IOException ex) {
                                    System.err.println("coudn't switch");
                                    ex.printStackTrace();
                                }
                            });
                        }
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.RECEIVING_MOVE))
                        {
                            Platform.runLater(() -> {
                                try
                                {
                                    printGameMove(xoMsg);
                                }
                                catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            });
                        }
                        
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.Chat_between_GamePlayer))
                        {                    
                            Platform.runLater(() -> {                              
                                PrintMessageOfChatRoom(xoMsg);                                    
                            });
                        }                        
                        
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.RETRIVEMOVES ))
                        {
                            Platform.runLater(() -> {
                                try
                                {
                                    DisplayMoves(xoMsg);
                                }
                                catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            });
                        }
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.GAME_ENDED_SECCUSSFULLY))
                        {
                            MI.recieveGameEnding();
                        }
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.BACK))
                        {
                         
                            Platform.runLater(() -> {
                                try {                                    
                                    switchToSelectionScene(stage);
                                } catch (IOException ex) {
                                    Logger.getLogger(TicTacToeGui.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });  
                        }
                                                
                        else if(xoMsg.getTypeOfOpearation().equals("gameIsNotSetted")){
                            System.err.println("gameIsNotSetted");
                        }
                    }
                     catch (IOException ex) {
                         try
                         {
                            this.dis.close();
                            ps.close();
                            this.mySocket.close();
                            break;                             
                         }
                         catch (IOException exception)
                         {
                             exception.printStackTrace();
                         }
                    }

                }
            }).start();
            
        } catch (IOException ex){
            System.err.println("Server Is Off");
            ex.printStackTrace();
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/signin/signIn.fxml"));
        Parent  root = loader.load(); 
        FXMLLoader signuppage=new FXMLLoader();
        signuppage.setLocation(getClass().getResource("/signup/signUp.fxml"));
        Parent  signuppageroot = signuppage.load();
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    
    
    void switchToSelectionScene(Stage stage) throws IOException
    {
        FXMLLoader selectionpage=new FXMLLoader();
        selectionpage.setLocation(getClass().getResource("/selectionmode/selectionmode.fxml"));
        Parent  selectionroot = selectionpage.load();
        Scene sceneselection = new Scene(selectionroot);
        stage.hide();
        stage.setScene(sceneselection);
        stage.show();
    }
    void switchToOnLineScene(Stage stage,XOInterface xoMssge) throws IOException{
        FXMLLoader onLinePage=new FXMLLoader();
        onLinePage.setLocation(getClass().getResource("/online/onLine.fxml"));
        Parent  onLineRoot = onLinePage.load();        
        OnLineController ON=onLinePage.getController();
        ON.setAllPlayers(xoMssge);        
//        ON.setControllerStreams(dis, ps);
        Scene sceneonline = new Scene(onLineRoot);
        stage.hide();
        stage.setScene(sceneonline);
        stage.show();
    }
    void switchToSinglePlayerScene(Stage stage) throws IOException {
        FXMLLoader singlePlayerPage=new FXMLLoader();
        singlePlayerPage.setLocation(getClass().getResource("/playwithcomputer/playWithComputer.fxml"));
        Parent  root = singlePlayerPage.load();
//        PlayWithComputerController SP = singlePlayerPage.getController();
//        SP.setControllerStreams(dis, ps);
        Scene scene = new Scene(root);
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
    void switchToOnpopupscene(XOInterface xoMsg){
        if(!xoMsg.getPlayer().getUserName().equals(SignInController.username))
        {    
            Platform.runLater(()->{
                try {
                    FXMLLoader popuppage=new FXMLLoader();
                    popuppage.setLocation(getClass().getResource("/onlinepopup/onLinePopup.fxml"));
                    Parent  popuppageroot = popuppage.load();
                    onLinePopupController popup=popuppage.getController(); 
                    popup.getusername( xoMsg.getPlayer().getUserName());

                    Scene scenepopup = new Scene( popuppageroot);
                    Stage popupstage =  new Stage() ;
                    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                    popupstage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 500);
                    popupstage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 150);
                    popupstage.initStyle(StageStyle.UNDECORATED);
                    popupstage.hide(); //optional
                    popupstage.setScene(scenepopup);
                    popupstage.show(); 
                    PauseTransition delay = new PauseTransition(Duration.seconds(4));
                    delay.setOnFinished( event ->  popupstage.close() );
                    delay.play();
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }); 

      }
    }
    
   void switchToLogIn (Stage stage)
    {
        try
        {
            FXMLLoader signinpage = new FXMLLoader();
            signinpage.setLocation(getClass().getResource("/signin/signIn.fxml"));
            Parent  signinpageroot = signinpage.load();
//            SignInController SI = signinpage.getController();
//            SI.setControllerStreams(dis, ps);
            Scene scenesignin = new Scene( signinpageroot);
            stage.hide();
            stage.setScene(scenesignin);
            stage.show();                     
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }     
    } 
   
   void switchToMultiPlayer (Stage stage, XOInterface xoMsg)
   {
        try
        {
            FXMLLoader multiPlayer = new FXMLLoader();
            multiPlayer.setLocation(getClass().getResource("/multiPlayers/multiPlayer.fxml"));
            Parent  multiPlayerPageRoot = multiPlayer.load();
            MI = multiPlayer.getController();
//            MI.setControllerStreams(dis, ps);
            MI.setIDs(xoMsg.getGameLog().getGameId(), SignInController.username, xoMsg.getGameLog().getHomePlayer());    
            Scene multiPlayerScene = new Scene(multiPlayerPageRoot);
            stage.hide();
            stage.setScene(multiPlayerScene);
            stage.show();                     
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }            
   }
   
    void switchToInvitationpopupscene(XOInterface xoMsg){
      
        Platform.runLater(()->{
            try {
                FXMLLoader popupInvitationpage=new FXMLLoader();
                popupInvitationpage.setLocation(getClass().getResource("/invitationpopup/invitationPopup.fxml"));
                Parent  invitationpageroot = popupInvitationpage.load();
                invitationPopupController popupInvitation = popupInvitationpage.getController();
//                popupInvitation.setControllerStreams(dis, ps);
                Scene scenepopupinvitation = new Scene( invitationpageroot);
                Stage popupinvitationstage =  new Stage() ;
                popupInvitation.getOpponentplayername(xoMsg,popupinvitationstage);                  
                popupinvitationstage.hide();
                popupinvitationstage.initStyle(StageStyle.UNDECORATED);
                popupinvitationstage.setScene(scenepopupinvitation); 
                popupinvitationstage.show(); 
              } 
            catch (IOException ex) 
            {
              Logger.getLogger(TicTacToeGui.class.getName()).log(Level.SEVERE, null, ex);
            }
          }); 
    }
    
    void printGameMove(XOInterface xoMsg)
    {
        MI.printOpponentMove(xoMsg.getFieldNumber(),true);

    }
    
    void PrintMessageOfChatRoom(XOInterface xoMsg)
    {
        MI.printMessage(xoMsg);
        String path = "sound.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaplayer = new MediaPlayer(media);
        mediaplayer.play();                  
//        MI.printMessage(xoMsg);
    }
    
    void  DisplayMoves(XOInterface xoMsg)
    {
        System.out.println(xoMsg.getGameLog().getHomePlayer());
        System.out.println(xoMsg.getGameLog().getOpponentPlayer());
        MI.displayMovesOnBoard(xoMsg.getGameLog().getSavedGame(),
                                xoMsg.getGameLog().getHomePlayer(),
                                xoMsg.getGameLog().getGameId());
    }
  
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
