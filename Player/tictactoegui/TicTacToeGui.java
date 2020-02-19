
package tictactoegui;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import playwithcomputer.PlayWithComputerController;
import selectionmode.selectionModeController;
import signin.*;
import signup.*;
import interfaces.*;
import online.OnLineController;


public class TicTacToeGui extends Application {
    DataInputStream dis;
    PrintStream ps;
    Socket mySocket;

    @Override
    public void start(Stage stage) throws Exception {
        try{
            mySocket = new Socket("10.145.3.114", 5000);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            new Thread(()->{
                while (true){
                    try {
                        String recievedMsg = dis.readLine();
                        Gson g = new Gson();
                        XOInterface xoMsg;
                        xoMsg = g.fromJson(recievedMsg, XOInterface.class);
                        if(xoMsg.getTypeOfOpearation().equals(Messages.NEW_PLAYER_LOGGED_IN)){
                            System.err.println("login here");
                            Platform.runLater(()->{
                                try {
                                    switchToSelectionScene(stage);
                                } catch (IOException ex) {
                                    System.err.println("coudn't switch");
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
//                        
//                     else if(xoMsg.getTypeOfOpearation().equals(Messages.)){
//                            System.err.println("login here");
//                            Platform.runLater(()->{
//                                try {
//                                    switchToSelectionScene(stage);
//                                } catch (IOException ex) {
//                                    System.err.println("coudn't switch");
//                                }
//                            });
//                        }
                        else if(xoMsg.getTypeOfOpearation().equals(Messages.RETREVING_PLAYERS_LIST))
                        {
                                       Platform.runLater(()->{
                                try {
                                   switchToOnLineScene(stage);
                                } catch (IOException ex) {
                                    System.err.println("coudn't switch");
                                }
                            });
                        
                            //  xoMsg.players                    

                                      }
                        
                        else{
                            
                        }
                    }

                     catch (IOException ex) {
                        System.err.println("errrrrrr");
                    }

                }
            }).start();
            
        } catch (IOException ex){
            System.err.println("ay 7aga");
            
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/signin/signIn.fxml"));
        Parent  root = loader.load();
      
        SignInController singIn =loader.getController();
        singIn.setControllerStreams(dis, ps);
        
                FXMLLoader signuppage=new FXMLLoader();
        signuppage.setLocation(getClass().getResource("/signup/signUp.fxml"));
        Parent  signuppageroot = signuppage.load();
        signUpController SU=signuppage.getController();
          SU.setControllerStreams(dis, ps);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    
    void switchToSelectionScene(Stage stage) throws IOException{
        FXMLLoader selectionpage=new FXMLLoader();
        selectionpage.setLocation(getClass().getResource("/selectionmode/selectionmode.fxml"));
        Parent  selectionroot = selectionpage.load();
        selectionModeController SM=selectionpage.getController();
          SM.setControllerStreams(dis, ps);
        Scene sceneselection = new Scene(selectionroot);
        stage.hide();
        stage.setScene(sceneselection);
        stage.show();
    }
    void switchToOnLineScene(Stage stage) throws IOException{
               FXMLLoader onLinePage=new FXMLLoader();
        onLinePage.setLocation(getClass().getResource("/online/onLine.fxml"));
        Parent  onLineRoot = onLinePage.load();
        OnLineController ON=onLinePage.getController();
                  ON.setControllerStreams(dis, ps);
        Scene sceneonline = new Scene(onLineRoot);
        stage.hide();
        stage.setScene(sceneonline);
        stage.show();
      
    }
   void switchToLogIn (Stage stage)
    {
        try
        {
            FXMLLoader signinpage = new FXMLLoader();
            signinpage.setLocation(getClass().getResource("/signin/signIn.fxml"));
            Parent  signinpageroot = signinpage.load();
            SignInController SI = signinpage.getController();
            SI.setControllerStreams(dis, ps);
            Scene scenesignin = new Scene( signinpageroot);
            stage.hide();
            stage.setScene(scenesignin);
            stage.show();                     
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }     
    } 
    
    
  
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
