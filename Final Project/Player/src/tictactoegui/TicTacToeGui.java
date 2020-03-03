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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
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
    public static SignInController SI;
    public static int score = 0;
    int counter = 0;
    String [] getServerSocket(){
        String line = "";
        try
        {
            
            File file = new File("connection.conf");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            line = br.readLine();

            System.out.println("Read text file using InputStreamReader");

            br.close();

        }
        catch (IOException ex)
        {
            System.err.println("server ip coudn't be loaded");
        }
        String[] config = line.split(":");
        return config;
    }
    @Override
    public void start(Stage stage) throws Exception 
    {
        try
        {
            String [] config = getServerSocket();
            mySocket = new Socket(config[0], Integer.parseInt(config[1]));
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
                        switch (xoMsg.getTypeOfOpearation()) {
                            case Messages.NEW_PLAYER_LOGGED_IN:
                                Platform.runLater(()->{
                                    try
                                    {
                                        switchToSelectionScene(stage,xoMsg);
                                    } catch (IOException ex) {
                                        System.err.println("coudn't switch");
                                        ex.printStackTrace();
                                    }
                                }); break;
                            case Messages.SIGN_UP_ACCEPTED:
                                System.err.println("Register here");
                                Platform.runLater(()->{
                                    switchToLogIn(stage);
                                }); break;
                            case Messages.NEW_PLAYER_LOGGEDIN_POP:
                                switchToOnpopupscene(xoMsg);
                                break;
                            case Messages.RECEIVING_INVITATION:
                                switchToInvitationpopupscene(xoMsg);
                                break;
                            case Messages.RETREVING_PLAYERS_LIST:
                                Platform.runLater(()->{
                                    try {
                                        switchToOnLineScene(stage,xoMsg);
                                    } catch (IOException ex) {
                                        System.err.println("coudn't switch");
                                        ex.printStackTrace();
                                    }
                                }); break;
                            case Messages.INVITATION_ACCEPTED:
                                Platform.runLater(() -> {
                                    switchToMultiPlayer(stage, xoMsg);
                                }); break;
                            case Messages.INVITATION_REJECTED:
                                SignInController.myTurn = false;
                                break;
                            case Messages.PLAYING_SINGLE_MODE:
                                Platform.runLater(()->{
                                    try
                                    {
                                        switchToSinglePlayerScene(stage);
                                    }
                                    catch (IOException ex) {
                                        System.err.println("coudn't switch");
                                        ex.printStackTrace();
                                    }
                                }); break;
                            case Messages.RECEIVING_MOVE:
                                Platform.runLater(() -> {
                                    try
                                    {
                                        printGameMove(xoMsg);
                                    }
                                    catch (Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }); break;
                            case Messages.Chat_between_GamePlayer:
                                Platform.runLater(() -> {
                                    PrintMessageOfChatRoom(xoMsg);
                                }); break;
                            case Messages.RETRIVEMOVES:
                                Platform.runLater(() -> {
                                    try
                                    {
                                        if (xoMsg.getGameLog().getGameId() != 0)
                                        {
                                            DisplayMoves(xoMsg);
                                            cancelResume(false);
                                        }
                                        else
                                        {
                                            cancelResume(true);
                                        }
                                    }
                                    catch (Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }); break;
                            case Messages.GAME_ENDED_SECCUSSFULLY:
                                MI.recieveGameEnding();
                                break;
                            case Messages.BACK:
                                Platform.runLater(() -> {
                                    try {
                                        switchToSelectionScene(stage,xoMsg);
                                    } catch (IOException ex) {
                                        Logger.getLogger(TicTacToeGui.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }); break;
                            case Messages.LOGIN:
                                Platform.runLater(() -> {
                                    SI.dispErrMsg();
                                });
                                break;                                
                            case "gameIsNotSetted":
                                System.err.println("gameIsNotSetted");
                                break;
                            default:
                                break;
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
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished( event -> {
                try {
                    start(stage);
                    counter ++;
                    if (counter > 30)
                    {
                        Platform.exit();
                    }                    
                }
                catch (Exception ex1) {
                    ex1.printStackTrace();
                }
            });
            delay.play();            
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/signin/signIn.fxml"));
        Parent  root = loader.load(); 
        SI = loader.getController();
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.setTitle("  Tic Tac Toe The Game");
        stage.setResizable(false);
        stage.getIcons().add(new Image("tictactoegui/logo.png")); 
        stage.show();
    }
    
    
    void switchToSelectionScene(Stage stage,XOInterface xoMessage) throws IOException
    {
        score = xoMessage.getPlayer().getScore();
        FXMLLoader selectionpage = new FXMLLoader();
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
        Scene sceneonline = new Scene(onLineRoot);
        stage.hide();
        stage.setScene(sceneonline);
        stage.show();
    }
    void switchToSinglePlayerScene(Stage stage) throws IOException {
        FXMLLoader singlePlayerPage=new FXMLLoader();
        singlePlayerPage.setLocation(getClass().getResource("/playwithcomputer/playWithComputer.fxml"));
        Parent  root = singlePlayerPage.load();
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
                    popupstage.hide();
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
            SI = signinpage.getController();
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
            SignInController.myTurn = xoMsg.getGameLog().getHomePlayer().equals(SignInController.username);
            MI = multiPlayer.getController();
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
        if (MultiPlayerController.turnOffNotification)
        {
            String path = "sound.mp3";
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaplayer = new MediaPlayer(media);
            mediaplayer.play();               
        }
    }
    
    void  DisplayMoves(XOInterface xoMsg)
    {
        MI.displayMovesOnBoard(xoMsg.getGameLog().getSavedGame(),
                                xoMsg.getGameLog().getHomePlayer(),
                                xoMsg.getGameLog().getGameId());
    }
    
    void cancelResume(boolean state)
    {
        MI.cancelOrEnableResume(state);
    }
    
    @Override
    public void stop()
    {
        try
        {
            Player player = new Player();            
            if (SignInController.username == null)
            {
               SignInController.username = "null";
            }
            player.setUserName(SignInController.username);
            XOInterface xointerface = new XOInterface(Messages.LOGOUT,player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            TicTacToeGui.ps.println(s);
            System.out.println(s);                  
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.play();
            ps.close();
            dis.close();            
            mySocket.close();            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        Platform.exit();        
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
