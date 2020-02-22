/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levelSelection;

import com.google.gson.Gson;
import interfaces.Messages;
import interfaces.Player;
import interfaces.XOInterface;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import signin.SignInController;

/**
 *
 * @author E.S
 */
public class LevelSelectionController implements Initializable {
    
    private Label label;
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    public static int gameLevel=0;
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
        
    }
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void easyMode(ActionEvent event) {
        Player player=new Player();
        player.setUserName(SignInController.username);
        XOInterface xointerface =new XOInterface (Messages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s); 
        gameLevel=0;
    }

    @FXML
    private void mediumMode(ActionEvent event) {
        Player player=new Player();
        player.setUserName(SignInController.username);
        XOInterface xointerface =new XOInterface (Messages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);
        gameLevel=1;
    }
    
}
