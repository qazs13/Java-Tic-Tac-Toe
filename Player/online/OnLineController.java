package online;

import com.google.gson.Gson;
import interfaces.Gamelog;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import signin.SignInController;
import interfaces.*;

public class OnLineController implements Initializable {
    
    
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    String homePlayer = SignInController.username;
    String opponentPlayer;
    
    
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        //opponentPlayer 
        Gamelog offlineGameCreation=new Gamelog();
        offlineGameCreation.setHomePlayer(homePlayer);
        offlineGameCreation.setOpponentPlayer(opponentPlayer);
        XOInterface xointerface =new XOInterface ("invite",offlineGameCreation);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
