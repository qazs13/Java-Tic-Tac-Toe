/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invitationpopup;

import com.google.gson.Gson;
import interfaces.Gamelog;
import interfaces.Messages;
import interfaces.XOInterface;
import java.io.DataInputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import signin.SignInController;
import static signin.SignInController.username;
import tictactoegui.TicTacToeGui;


public class invitationPopupController implements Initializable {
    
    @FXML
    private Text poptextinvitation;
//    DataInputStream controllerDIS;
    PrintStream controllerPS;
    String homeplayer = SignInController.username;
    String opponentPlayer;
    XOInterface xoMssge;
    Stage stage;
//    
//    public void setControllerStreams(DataInputStream dis, PrintStream ps){
//        controllerDIS = dis;
//        controllerPS = ps;
//    }
//    
    public void getOpponentplayername(XOInterface xoMssge,Stage stage){
        this.xoMssge = xoMssge;
        this.opponentPlayer = xoMssge.getGameLog().getHomePlayer();
        this.stage = stage;
        poptextinvitation.setText("An invitation request came from: "+ opponentPlayer);
    }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controllerPS = TicTacToeGui.ps;
        
    }   
    
    @FXML
    private void accept(ActionEvent event) {        
        xoMssge.setTypeOfOpearation(Messages.INVITATION_ACCEPTED);
        Gson g = new Gson();
        String s = g.toJson(xoMssge);
        controllerPS.println(s);
        stage.hide();
        String path = "game.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaplayer = new MediaPlayer(media);
        mediaplayer.play();
    }

    @FXML
    private void decline(ActionEvent event) {
       xoMssge.getGameLog().setOpponentPlayer(homeplayer);
       xoMssge.setTypeOfOpearation(Messages.INVITATION_REJECTED);
       Gson g = new Gson();
       String s = g.toJson(xoMssge);
       controllerPS.println(s);
       stage.hide();
    }
    
}
