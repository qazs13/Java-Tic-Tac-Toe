/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selectionmode;

import interfaces.Messages;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import online.OnLineController;
import signup.signUpController;
import interfaces.*;
import javafx.scene.text.Text;
import levelSelection.LevelSelectionController;
import signin.SignInController;


public class selectionModeController implements Initializable {
    
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    Stage window;
    @FXML
    private Text myName;
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    public void setStage(){
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myName.setText(SignInController.username);
    }    

    @FXML
    private void multiplayer(ActionEvent event) throws IOException {
        Player player = new Player();
        player.setUserName(SignInController.username); 
        XOInterface xointerface =new XOInterface (Messages.GET_PLAYERS,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);

    }

    @FXML
    private void singlePlayer(ActionEvent event) throws IOException { 
        FXMLLoader levelSelection=new FXMLLoader();
        levelSelection.setLocation(getClass().getResource("/levelSelection/levelSelection.fxml"));
        Parent  levelSelectionroot = levelSelection.load();
        LevelSelectionController lsc=levelSelection.getController();
        lsc.setControllerStreams(controllerDIS, controllerPS);
        Scene scenelevelSelection = new Scene(levelSelectionroot);
        Stage signupstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        signupstage.hide(); //optional
        signupstage.setScene(scenelevelSelection);
        signupstage.show();         
        
//        Player player=new Player();
//        player.setUserName(SignInController.username);
//        XOInterface xointerface =new XOInterface (Messages.PLAYING_SINGLE_MODE,player);
//        Gson g = new Gson();
//        String s = g.toJson(xointerface);
//        controllerPS.println(s);
    }
}
