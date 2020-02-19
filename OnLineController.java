/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author E.S
 */
public class OnLineController implements Initializable {
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
    
}
