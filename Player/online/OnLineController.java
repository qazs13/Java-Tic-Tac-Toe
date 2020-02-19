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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<String, String> userName;
    String homePlayer = SignInController.username;
    String opponentPlayer;    
    
    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Vector<Player> allPlayers = new Vector<>();
        Player play1 = new Player("amr", true, 0);
        Player play2 = new Player("hakim", true, 0);
        Player play3 = new Player("mayar", true, 0);
        allPlayers.add(play3);
        allPlayers.add(play2);
        allPlayers.add(play1);
        ObservableList<Player> _allPlayers = FXCollections.observableList(allPlayers);
        table.setItems(_allPlayers);
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
        controllerPS.println(s);        
    }

    @FXML
    private void MouseClicked(MouseEvent event) {
        player = table.getSelectionModel().getSelectedItem();
    }


    
}
