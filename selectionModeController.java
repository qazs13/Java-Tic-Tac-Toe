package selectionmode;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tictactoegui.Gamelog;
import tictactoegui.XOInterface;
import tictactoegui.Messages;
import signin.SignInController;


public class selectionModeController implements Initializable {

    
//Edit by Mayar
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    String homePlayer = SignInController.username;
    String opponentPlayer;
    
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    
    @FXML
    private void PlayingSingleMode(ActionEvent event) {
        Gamelog offlineGameCreation=new Gamelog();
        offlineGameCreation.setHomePlayer(homePlayer);
        offlineGameCreation.setOpponentPlayer(opponentPlayer);
//        if(homePlayer.equals(opponentPlayer))
//        {
            XOInterface xointerface =new XOInterface (Messages.PLAYING_SINGLE_MODE,offlineGameCreation);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            controllerPS.println(s); 
//        }
      
    }
//End of Editing 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//        @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//    }  
    
}
