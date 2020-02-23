
package levelSelection;

import com.google.gson.Gson;
import interfaces.Messages;
import interfaces.Player;
import interfaces.XOInterface;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import signin.SignInController;
import tictactoegui.TicTacToeGui;

/**
 *
 * @author E.S
 */
public class LevelSelectionController implements Initializable {
    
    private Label label;
    PrintStream controllerPS;
    public static int gameLevel=0;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controllerPS = TicTacToeGui.ps;
    }    

    @FXML
    private void easyMode(ActionEvent event) {
        Player player=new Player();
        player.setUserName(SignInController.username);
        XOInterface xointerface =new XOInterface (Messages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s); 
        gameLevel=0;
    }

    @FXML
    private void mediumMode(ActionEvent event) {
        Player player=new Player();
        player.setUserName(SignInController.username);
        XOInterface xointerface =new XOInterface (Messages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);
        gameLevel=1;
    }
    
    @FXML
    private void minimize(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void exit(ActionEvent event) {
        Player player=new Player();
        player.setUserName(SignInController.username);
        XOInterface xointerface =new XOInterface (Messages.LOGOUT,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);        
        Platform.exit();
    }          
}
