package onlinepopup;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class onLinePopupController implements Initializable {

    @FXML
    private Text poptext;
    String uname;

     public void getusername( String username){
       uname = username;
       poptext.setText(uname + " is now on line");
     }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
