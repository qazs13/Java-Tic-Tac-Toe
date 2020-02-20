/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selectionmode;

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
import signin.SignInController;


public class selectionModeController implements Initializable {
    
     DataInputStream controllerDIS;
    PrintStream controllerPS;
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void multiplayer(ActionEvent event) throws IOException {
        Player player=new Player();
        player.setUserName(SignInController.username); 
        XOInterface xointerface =new XOInterface (Messages.GET_PLAYERS,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);

    }
    
}
