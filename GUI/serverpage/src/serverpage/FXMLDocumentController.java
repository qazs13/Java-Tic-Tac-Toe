/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpage;

import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import server.Server;
/**
 *
 * @author E.S
 */

class ServerThread extends Thread
{
    Server server;
    public void run ()
    {
        server = new Server();
        System.out.println(server);
        server.runServer();
        
    }

    public void stopThread()
    {
        server.stopServer();
        System.out.println(server);
        this.stop();
    }
}

public class FXMLDocumentController {
    
    private Label label;
    ServerThread serverThread;
    
    @FXML
    private void serverOn(ActionEvent event) {
        serverThread = new ServerThread();
        serverThread.start();
    }
    
    @FXML
    private void serverOff(ActionEvent event) {
        serverThread.stopThread();
    }
    
}
