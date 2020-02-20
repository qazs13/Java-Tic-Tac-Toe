package serverpage;

import database.Database;
import interfaces.Player;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Server;
/**
 *
 * @author E.S
 */

class ServerThread extends Thread
{
    Server server;
    public void run ()
    {
        server = new Server();
        server.runServer();
    }

    public void stopThread()
    {
        server.stopServer();
        System.out.println(server);
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
    
    void fetchPlayers(){
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
    }
    
    @FXML
    private void serverOff(ActionEvent event) {
        db.makeAllPlayersOffline();
        serverThread.stopThread();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
        userNameCol.setStyle("-fx-alignment: CENTER;");
        scoreCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setStyle("-fx-alignment: CENTER;");
    }
    
}
