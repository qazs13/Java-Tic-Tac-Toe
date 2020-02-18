/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signup;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import signin.SignInController;
import  tictactoegui.*;

public class signUpController implements Initializable {

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField uname;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField conpass;
    @FXML
    private Text checkfname;
    @FXML
    private Text checklname;
    @FXML
    private Text checkuname;
    @FXML
    private Text checkpass;
    @FXML
    private Text confirmpass;
 
    public boolean check1()
    {
        boolean flag = true;
        checkuname.setVisible(false);
        checkpass.setVisible(false);
        checklname.setVisible(false);
        checkfname.setVisible(false);
        confirmpass.setVisible(false);
        if(uname.getText().equals(""))
        {
            checkuname.setVisible(true);
            flag = false;
        }
        if(password.getText().equals(""))
        {
            checkpass.setVisible(true); 
            flag = false;
        }
        if(lname.getText().equals(""))
        {
            checklname.setVisible(true);
            flag = false;
        }
        if(fname.getText().equals(""))
        {
            checkfname.setVisible(true); 
            flag = false;
        }
        if(((conpass.getText()).equals(password.getText())) == false)
        {
            confirmpass.setVisible(true); 
            flag = false;
        }
//        else{
//         flag = true;
//        }
        return flag;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    

    @FXML
    private void register(ActionEvent event) throws IOException {
      boolean b=check1();
      if (b==true)
      {
   FXMLLoader signinpage=new FXMLLoader();
   signinpage.setLocation(getClass().getResource("/signin/signIn.fxml"));
        Parent  signinpageroot = signinpage.load();
        SignInController FS=signinpage.getController();
        
        Scene scenesignin = new Scene( signinpageroot);
 Stage signinstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            signinstage.hide(); //optional
            signinstage.setScene(scenesignin);
           signinstage.show(); 
       
      }

    }
    
}