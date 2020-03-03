package serverpage;

import database.Database;
import interfaces.Player;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Server;

class ServerThread extends Thread
{
    Server server;
    @Override
    public void run ()
    {
        server = new Server();
        server.runServer();
    }

    public void stopThread()
    {
        if (server != null)
        {
            server.stopServer();
        }
        this.stop();                
    }
}

public class ServerPageController implements Initializable {
    private Label label;
    ServerThread serverThread;
    Database db = new Database();
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
    private Button serverON;
    @FXML
    private Button serverOff;
    public void fetchPlayers(){
        allPlayers = db.retriveAllPlayers().Players;
        ObservableList<Player> _allPlayers = FXCollections.observableList(allPlayers);
        playersTable.setItems(_allPlayers);
    }
    
    @FXML
    private void serverOn(ActionEvent event) {
        db.makeAllPlayersOffline();
        serverThread = new ServerThread();
        serverThread.start();
        fetchPlayers();
        serverON.setDisable(true);
        serverOff.setDisable(false);        
    }
    
    @FXML
    private void serverOff(ActionEvent event) 
    {
        stop();
        ObservableList<Player> _allPlayers = FXCollections.observableList(allPlayers);
        playersTable.setItems(_allPlayers);
        allPlayers.clear();
        serverOff.setDisable(true);
        serverON.setDisable(false);  
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
        userNameCol.setStyle("-fx-alignment: CENTER;");
        scoreCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setStyle("-fx-alignment: CENTER;");
        serverOff.setDisable(true);
    }  
    
    public void stop()
    {
        db.makeAllPlayersOffline();
        if (serverThread != null)
        {
            serverThread.stopThread();             
        }
    }
}
