
package signin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
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

public class SignInController implements Initializable {
String username;
String password;
    @FXML
    Button signup;
   @FXML
 private AnchorPane main;
    @FXML
    private    Button login;
    @FXML
    private TextField loginusername;
    @FXML
    private PasswordField loginpassword;
    @FXML
   private Text checkusername;
        @FXML
   private Text checkpassword;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void signup(ActionEvent event) throws IOException {
      //  AnchorPane root=FXMLLoader.load(getClass().getResource("signUp.fxml"));
           Parent  root = FXMLLoader.load(getClass().getResource("/signup/signUp.fxml"));
          Scene scene = new Scene(root);
           Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
           window.setScene(scene);
           window.show();
            //root = FXMLLoader.load(TicTacToeGui.class.getResource("signUp.fxml"));
           // main.getChildren().addAll(root);
        
        //stage.show();
    }

    @FXML
    private void login(ActionEvent event) {
        username=loginusername.getText();
        password=loginpassword.getText();
       if(username.equals(""))
       {
       checkusername.setVisible(true);
       }
       else if(password.equals(""))
       {
          checkpassword.setVisible(true); 
       }
       else{
           Player player=new Player();
           player.setUserName(username);
           player.setPasswd(password);
          XOInterface xointerface =new XOInterface ("login",player);
       }
      
    }

  
    
}
