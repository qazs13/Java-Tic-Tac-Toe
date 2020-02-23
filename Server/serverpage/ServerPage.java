package serverpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ServerPage extends Application {

    public static ServerPageController spc;
    @Override
    public void start(Stage stage) throws Exception {        
        //Parent root = FXMLLoader.load(getClass().getResource("ServerPage.fxml"));
        FXMLLoader ServerPage=new FXMLLoader();
        ServerPage.setLocation(getClass().getResource("ServerPage.fxml"));
        Parent  ServerPageroot = ServerPage.load();
        spc=ServerPage.getController();
        Scene scene = new Scene(ServerPageroot);
        stage.setScene(scene);
        stage.setTitle("Server Page");
        stage.show();
        //ServerPageController updateList
    }


    
    public static void main(String[] args) {
        launch(args);        
    }   
}