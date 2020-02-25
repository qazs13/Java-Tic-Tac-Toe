package online;

import com.google.gson.Gson;
import interfaces.Gamelog;
import interfaces.Messages;
import interfaces.Player;
import interfaces.XOInterface;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import signin.SignInController;
import tictactoegui.TicTacToeGui;


public class OnLineController implements Initializable {
    PrintStream controllerPS;
    Player player;
    boolean inviteName = false;

    String homePlayer = SignInController.username;
    String opponentPlayer;    
    Vector<Player> allPlayers;
    @FXML
    private TableView<Player> playersTable;    
    @FXML
    private TableColumn<Player, String> userNameCol;
    @FXML
    private TableColumn<Player, Integer> scoreCol;
    @FXML
    private TableColumn<Player, String> statusCol;
    @FXML
    private TableColumn<Player, String> isPlayingCol;
    
    public void setAllPlayers (XOInterface xoMssge)
    {
        userNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
        isPlayingCol.setCellValueFactory(new PropertyValueFactory<Player, String>("IsPlaying"));
        userNameCol.setStyle("-fx-alignment: CENTER;");
        scoreCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setStyle("-fx-alignment: CENTER;");
        isPlayingCol.setStyle("-fx-alignment: CENTER;");
        allPlayers = xoMssge.Players;
        fetchPlayers();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controllerPS = TicTacToeGui.ps;

    }    
    
    void fetchPlayers(){
        ObservableList<Player> _allPlayers = FXCollections.observableList(allPlayers);
        playersTable.setItems(_allPlayers);
        playersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }    


    @FXML
    private void GetNames(ActionEvent event) {
        if (!player.getUserName().equals(SignInController.username))
        {
            opponentPlayer = player.getUserName();
            Gamelog offlineGameCreation = new Gamelog();
            offlineGameCreation.setHomePlayer(homePlayer);
            offlineGameCreation.setOpponentPlayer(opponentPlayer);
            XOInterface xointerface =new XOInterface (Messages.INVITE, offlineGameCreation);
            SignInController.myTurn = true;
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            System.out.println(s);
            controllerPS.println(s);             
        }
    }

    @FXML
    private void MouseClicked(MouseEvent event) {
        player = playersTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void back(ActionEvent event) {
        try
        {
            FXMLLoader signinpage=new FXMLLoader();
            signinpage.setLocation(getClass().getResource("/selectionmode/selectionmode.fxml"));
            Parent  signinpageroot = signinpage.load();
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
