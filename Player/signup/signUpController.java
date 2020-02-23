/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signup;


import com.google.gson.Gson;
import interfaces.Messages;
import interfaces.Player;
import interfaces.XOInterface;
import java.io.DataInputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import signin.SignInController;
import  tictactoegui.*;

public class signUpController implements Initializable {
//        DataInputStream controllerDIS;
    PrintStream controllerPS;
//        public void setControllerStreams(DataInputStream dis, PrintStream ps){
//        controllerDIS = dis;
//        controllerPS = ps;
//    }

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

        return flag;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controllerPS = TicTacToeGui.ps;
    }    

    @FXML
    private void register(ActionEvent event) throws IOException {
      boolean b=check1();
       Player player=new Player(uname.getText(),password.getText(),fname.getText(),lname.getText());

            XOInterface xointerface =new XOInterface (Messages.REGISTER,player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            controllerPS.println(s);  
//      controllerPS.println("reg msg");
//      if (b==true)
//      {
////           Player player=new Player(uname.getText(),password.getText(),fname.getText(),lname.getText());
////
////            XOInterface xointerface =new XOInterface (Messages.REGISTER,player);
////            Gson g = new Gson();
////            String s = g.toJson(xointerface);
////            controllerPS.println(s);  
//          
//          
////   FXMLLoader signinpage=new FXMLLoader();
////   signinpage.setLocation(getClass().getResource("/signin/signIn.fxml"));
////        Parent  signinpageroot = signinpage.load();
////        SignInController FS=signinpage.getController();
////        
////        Scene scenesignin = new Scene( signinpageroot);
//// Stage signinstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
////            signinstage.hide(); //optional
////            signinstage.setScene(scenesignin);
////           signinstage.show(); 
//       
//      }

    }
    
}