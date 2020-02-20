/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online;

import com.google.gson.Gson;
import interfaces.Gamelog;
import interfaces.Player;
import interfaces.XOInterface;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import signin.SignInController;

/**
 *
 * @author E.S
 */
public class OnLineController implements Initializable {
    DataInputStream controllerDIS;
    PrintStream controllerPS;
    Player player;

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
    
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    
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

    }    
    
    void fetchPlayers(){
        ObservableList<Player> _allPlayers = FXCollections.observableList(allPlayers);
        playersTable.setItems(_allPlayers);
    }    


    @FXML
    private void GetNames(ActionEvent event) {
        opponentPlayer = player.getUserName();
        Gamelog offlineGameCreation=new Gamelog();
        offlineGameCreation.setHomePlayer(homePlayer);
        offlineGameCreation.setOpponentPlayer(opponentPlayer);
        XOInterface xointerface =new XOInterface ("invite",offlineGameCreation);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        System.out.println(s);
        controllerPS.println(s);        
    }

    @FXML
    private void MouseClicked(MouseEvent event) {
        player = playersTable.getSelectionModel().getSelectedItem();
    }
}
