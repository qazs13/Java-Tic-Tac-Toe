
package signin;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintStream;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import static tictactoegui.TicTacToeGui.main;
import signup.*;
import  tictactoegui.*;
import interfaces.*;

public class SignInController implements Initializable {
    public static String username;
    String password;
   @FXML
    Button signup;
   @FXML
 private AnchorPane main;
    @FXML
       Button login;
    @FXML
     TextField loginusername;
    @FXML
     PasswordField loginpassword;
    @FXML
   Text checkusername;
        @FXML
    Text checkpassword;  
  Stage window;
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    

    @FXML
     void signup(ActionEvent event) throws IOException {
        FXMLLoader signuppage=new FXMLLoader();
        signuppage.setLocation(getClass().getResource("/signup/signUp.fxml"));
        Parent  signuppageroot = signuppage.load();
        signUpController su=signuppage.getController();
        su.setControllerStreams(controllerDIS, controllerPS);
        Scene scenesignup = new Scene(signuppageroot);
        Stage signupstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        signupstage.hide(); //optional
        signupstage.setScene(scenesignup);
        signupstage.show(); 
    }

@FXML
    private void login(ActionEvent event) {
        username = loginusername.getText();
        password = loginpassword.getText();
       if(username.equals("") || password.equals(""))
       {
            if (username.equals(""))
            {
                checkusername.setVisible(true);
            }
            else
            {
                checkusername.setVisible(false);
            }
            if(password.equals(""))
            {
                checkpassword.setVisible(true);
            }
            else
            {
                checkpassword.setVisible(false);
            }
       }
       else{
            checkusername.setVisible(false);
            checkpassword.setVisible(false);
            Player player=new Player();
            player.setUserName(username);
            player.setPasswd(password);
            XOInterface xointerface =new XOInterface (Messages.LOGIN,player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            controllerPS.println(s);
//            SocketPlayer socket = SocketPlayer.socketPlayer;
//            socket.sendMessageToServer(s);
//            s = socket.ReciveMessageFromServer();
            if (s != null)
            {
                System.out.println(s);
            }
       }
      
    }

  
    
}
