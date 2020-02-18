
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
import signin.*;
import signup.*;



public class TicTacToeGui extends Application {
    DataInputStream dis;
    PrintStream ps;
    Socket mySocket;

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
                        /*
                        else if (///////////////////)
                        {
                           
                        }
                        else
                        {
                         /////////////////////   
                        }
*/
                    } catch (IOException ex) {
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
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    
    void switchToSelectionScene(Stage stage) throws IOException{
        FXMLLoader selectionpage=new FXMLLoader();
        selectionpage.setLocation(getClass().getResource("/selectionmode/selectionmode.fxml"));
        Parent  selectionroot = selectionpage.load();

        Scene sceneselection = new Scene(selectionroot);
        stage.hide();
        stage.setScene(sceneselection);
        stage.show();
    }
    
    
  
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
