package invitationpopup;

import com.google.gson.Gson;
import interfaces.Messages;
import interfaces.XOInterface;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import signin.SignInController;
import tictactoegui.TicTacToeGui;


public class invitationPopupController implements Initializable {
    
    @FXML
    private Text poptextinvitation;
    PrintStream controllerPS;
    String homeplayer = SignInController.username;
    String opponentPlayer;
    XOInterface xoMssge;
    Stage stage;

    public void getOpponentplayername(XOInterface xoMssge,Stage stage){
        this.xoMssge = xoMssge;
        this.opponentPlayer = xoMssge.getGameLog().getHomePlayer();
        this.stage = stage;
        poptextinvitation.setText("An invitation request came from: "+ opponentPlayer);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controllerPS = TicTacToeGui.ps;
        
    }   
    
    @FXML
    private void accept(ActionEvent event) {        
        xoMssge.setTypeOfOpearation(Messages.INVITATION_ACCEPTED);
        Gson g = new Gson();
        String s = g.toJson(xoMssge);
        controllerPS.println(s);
        stage.hide();
    }

    @FXML
    private void decline(ActionEvent event) {
       xoMssge.getGameLog().setOpponentPlayer(homeplayer);
       xoMssge.setTypeOfOpearation(Messages.INVITATION_REJECTED);
       Gson g = new Gson();
       String s = g.toJson(xoMssge);
       controllerPS.println(s);
       stage.hide();
    }
    
}
