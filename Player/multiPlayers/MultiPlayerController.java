/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiPlayers;

import interfaces.Player;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import signin.SignInController;


public class MultiPlayerController implements Initializable {
    
    private Label label;
    String name;
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    Player player;    
    
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }    
    
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name = SignInController.username;
    }    
    
}
