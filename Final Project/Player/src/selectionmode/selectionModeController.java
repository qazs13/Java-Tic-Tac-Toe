package selectionmode;

import interfaces.Messages;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import interfaces.*;
import javafx.scene.text.Text;
import signin.SignInController;
import tictactoegui.TicTacToeGui;


public class selectionModeController implements Initializable {
    
    PrintStream controllerPS;
    Stage window;
    @FXML
    private Text myName;
    @FXML
    private Text myName1;
    @FXML
    private Text myScore;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controllerPS = TicTacToeGui.ps;
        myName.setText(SignInController.username);
        myScore.setText(Integer.toString(TicTacToeGui.score));
    }    

    @FXML
    private void multiplayer(ActionEvent event) throws IOException {
        Player player = new Player();
        player.setUserName(SignInController.username); 
        XOInterface xointerface =new XOInterface (Messages.GET_PLAYERS,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);

    }

    @FXML
    private void singlePlayer(ActionEvent event) throws IOException { 
        FXMLLoader levelSelection=new FXMLLoader();
        levelSelection.setLocation(getClass().getResource("/levelSelection/levelSelection.fxml"));
        Parent  levelSelectionroot = levelSelection.load();
        Scene scenelevelSelection = new Scene(levelSelectionroot);
        Stage signupstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        signupstage.hide(); 
        signupstage.setScene(scenelevelSelection);
        signupstage.show();         
    }

    @FXML
    private void logout(ActionEvent event) {
        try
        {
            Player player=new Player();
            if (SignInController.username == null)
            {
                SignInController.username = "null";
            }
            player.setUserName(SignInController.username);
            XOInterface xointerface =new XOInterface (Messages.LOGOUT,player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            controllerPS.println(s);    
            FXMLLoader signinpage=new FXMLLoader();
            signinpage.setLocation(getClass().getResource("/signin/signIn.fxml"));
            Parent  signinpageroot = signinpage.load();
            TicTacToeGui.SI = signinpage.getController();
            Scene scenesignin = new Scene( signinpageroot);
            Stage signinstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            signinstage.hide();
            signinstage.setScene(scenesignin);
            signinstage.show();             
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}