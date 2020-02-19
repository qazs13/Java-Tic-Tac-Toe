package onlinepopup;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class onLinePopupController implements Initializable {

    @FXML
    private Text poptext;
    
          DataInputStream controllerDIS;
    PrintStream controllerPS;
    String uname;
  
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
     public void getusername( String username){
       uname=username;
       poptext.setText(uname + "is now on line");
     }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
