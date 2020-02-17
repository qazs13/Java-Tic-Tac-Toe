
package signin;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import selectionmode.selectionModeController;
import static tictactoegui.TicTacToeGui.main;
import signup.*;
import  tictactoegui.*;

public class SignInController implements Initializable {
    String username;
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
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    

    @FXML
     void signup(ActionEvent event) throws IOException {
   FXMLLoader signuppage=new FXMLLoader();
   signuppage.setLocation(getClass().getResource("/signup/signUp.fxml"));
        Parent  signuppageroot = signuppage.load();
       signUpController su=signuppage.getController();
        
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
            XOInterface xointerface =new XOInterface ("login",player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
//            SocketPlayer socket = SocketPlayer.socketPlayer;
//            socket.sendMessageToServer(s);
//            s = socket.ReciveMessageFromServer();
//            if (s != null)
//            {
//                System.out.println(s);
//            }
if(true)
{
                try {
                    FXMLLoader selectionpage=new FXMLLoader();
                    selectionpage.setLocation(getClass().getResource("/selectionmode/selectionmode.fxml"));
                    Parent  selectionroot = selectionpage.load();
                    selectionModeController sm= selectionpage.getController();
                    
                    Scene sceneselection = new Scene(selectionroot);
                    Stage selectionstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    selectionstage.hide(); //optional
                    selectionstage.setScene(sceneselection);   
                    selectionstage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
}
       }
      
    }

  
    
}
