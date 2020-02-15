
package tictactoegui;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import signin.*;
import signup.*;



public class TicTacToeGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent  root = FXMLLoader.load(getClass().getResource("/signin/signIn.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
  
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
