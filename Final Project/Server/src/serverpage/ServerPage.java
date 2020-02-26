package serverpage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class ServerPage extends Application {

    public static ServerPageController spc;
    @Override
    
    public void start(Stage stage) throws Exception {        
        FXMLLoader ServerPage=new FXMLLoader();
        ServerPage.setLocation(getClass().getResource("ServerPage.fxml"));
        Parent  ServerPageroot = ServerPage.load();
        spc = ServerPage.getController();
        Scene scene = new Scene(ServerPageroot);
        stage.setScene(scene);
        stage.setTitle("  Tic Tac Toe The Server");
        stage.setResizable(false);
        stage.getIcons().add(new Image("serverpage/logo.png"));         
        stage.show();
    }

    @Override
    public void stop()
    {
        ServerThread s = new ServerThread();
        s.stopThread();
        Platform.exit();        
    }
    
    
    public static void main(String[] args) {
        launch(args);        
    }   
}