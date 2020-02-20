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
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import static signin.SignInController.username;


public class invitationPopupController implements Initializable {
    
    @FXML
    private Text poptextinvitation;
  DataInputStream controllerDIS;
    PrintStream controllerPS;
    String homeplayer;
  
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    
         public void gethomeplayername( String name){
       homeplayer=name;
      poptextinvitation.setText( "invitation request from"+ homeplayer);
     }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
Gamelog gamelog=new Gamelog();
    @FXML
    private void accept(ActionEvent event) {
     gamelog.setHomePlayer(homeplayer);
            XOInterface xointerface =new XOInterface (Messages.ACCEPT,gamelog);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            controllerPS.println(s);
    }

    @FXML
    private void decline(ActionEvent event) {
             gamelog.setHomePlayer(homeplayer);
            XOInterface xointerface =new XOInterface (Messages.DECLINE,gamelog);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            controllerPS.println(s);
    }
    
}
