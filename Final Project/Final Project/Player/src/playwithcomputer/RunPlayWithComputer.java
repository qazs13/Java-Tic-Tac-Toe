/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playwithcomputer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import signin.SignInController;

/**
 *
 * @author THE PR!NCE
 */
public class RunPlayWithComputer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        
        loader.setLocation(getClass().getResource("playWithComputer.fxml"));
        Parent  root = loader.load();
//        PlayWithComputerController.init();
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch();
    }
    
}
