
package tictactoegui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import playwithcomputer.PlayWithComputerController;
import signin.*;
import signup.*;



public class TicTacToeGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {

//        Parent  root = FXMLLoader.load(getClass().getResource("/signin/signIn.fxml"));

        Parent  root = FXMLLoader.load(getClass().getResource("/playwithcomputer/playWithComputer.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/playwithcomputer/style.css");
        PlayWithComputerController.init();
        
        stage.setScene(scene);
        stage.show();
    }
    
  
    
    public static void main(String[] args) throws FileNotFoundException {

        Application.launch(args);
    }
    
}
