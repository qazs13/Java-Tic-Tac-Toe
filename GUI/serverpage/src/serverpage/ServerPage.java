/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverpage;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.Database;
import interfaces.Player;
import java.util.Vector;
import server.Server;

/**
 *
 * @author E.S
 */
public class ServerPage extends Application {

    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerPage.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
//        Server server = new Server();
//        server.runServer();
//        allPlayers = db.retriveAllPlayers().Players;

//        database.Database db = new Database();
//        System.err.println(db.retriveAllPlayers().Players.get(0).getUserName());
        
    }
    
}
