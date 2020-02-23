package multiPlayers;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
import com.google.gson.Gson;
import interfaces.Gamelog;
import interfaces.Messages;
import interfaces.Player;
import interfaces.XOInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import signin.SignInController;


public class MultiPlayerController implements Initializable {

    boolean gameEnded;
    @FXML
    private Button pos7;
    @FXML
    private Button pos8;
    @FXML
    private Button pos9;
    @FXML
    private Button pos6;
    @FXML
    private Button pos5;
    @FXML
    private Button pos4;
    @FXML
    private Button pos2;
    @FXML
    private Button pos3;
    @FXML
    private Button pos1;
    private Label userNameLabel;
    @FXML
    private Label gameResult;

    DataInputStream controllerDIS;
    PrintStream controllerPS;
    boolean myturn;
    String myUserName;
    String opponentUserName;
    int gameID;
    char playerSymbol, opponentSymbol;
    Integer playerPos;
    Vector<Integer> playerMoves= new Vector<>();
    Vector<Integer> opponentMoves= new Vector<>();
    Vector<Integer> movesPool= new Vector<>();
    int numOfMoves;
    @FXML
    private Text homePlayerSign;
    @FXML
    private Label homeNameLabel;
    @FXML
    private Label opponentNameLabel;
    @FXML
    private Text opponenPlayerSign;
    @FXML
    private TextArea textScreenMessanger;  /////The Main Screen Ally haytl3 3lyha el klam///////
    @FXML
    private Button sendButton;  /////////Send Button///////////
    @FXML
    private TextField textAreaMessanger; ///////The Input Screen Ally hakteb feha el resala//////////

    boolean isWinningPosition(Vector<Integer> moves){
        boolean winFlag = false;
        Integer []  topRow = {1, 2, 3};
        Integer []  midRow = {4, 5, 6};
        Integer []  botRow = {7, 8, 9};
        Integer []  leftCol = {1, 4, 7};
        Integer []  midCol = {2, 5, 8};
        Integer []  rightCol = {3, 6, 9};
        Integer []  mainDiag = {1, 5, 9};
        Integer []  secondaryDiag = {3, 5, 7};
        Integer [][] winningCases = {
                topRow, midRow, botRow,
                leftCol, midCol, rightCol,
                mainDiag, secondaryDiag
        };

        int i=0;
        while(!winFlag && i<winningCases.length){
            if(moves.containsAll(Arrays.asList(winningCases[i])))
                winFlag = true;
            i++;
        }
        return winFlag;
    }
    public void init(){
        playerMoves.clear();
        opponentMoves.clear();
        movesPool.clear();
        if(SignInController.myTurn){
            playerSymbol = 'X';
            opponentSymbol = 'O';
        }
        else
        {
            playerSymbol = 'O';
            opponentSymbol = 'X';        
        }
        for(int i=0; i<9; i++)
            movesPool.add(i+1);
        numOfMoves = 0;
        gameEnded = false;
    }

    public void displayMove(Integer position, char symbol)
    {
        switch (position) {
            case 1:
                pos1.setText(Character.toString(symbol));
                break;
            case 2:
                pos2.setText(Character.toString(symbol));
                break;
            case 3:
                pos3.setText(Character.toString(symbol));
                break;
            case 4:
                pos4.setText(Character.toString(symbol));
                break;
            case 5:
                pos5.setText(Character.toString(symbol));
                break;
            case 6:
                pos6.setText(Character.toString(symbol));
                break;
            case 7:
                pos7.setText(Character.toString(symbol));
                break;
            case 8:
                pos8.setText(Character.toString(symbol));
                break;
            case 9:
                pos9.setText(Character.toString(symbol));
                break;
            default:
                break;
        }
    }
    @FXML
    void playMove(ActionEvent event) {
        if (myturn)
        {
            if (!gameEnded) {
                // Player move
                playerPos = Integer.parseInt(((Control) event.getSource()).getId());
                if (!movesPool.isEmpty() && movesPool.contains(playerPos)) {
                    displayMove(playerPos, playerSymbol);
                    movesPool.remove(playerPos);
                    playerMoves.add(playerPos);
                    sendMyMove();
                    numOfMoves++;
                    myturn = false;
                    if(isWinningPosition(playerMoves)){
                        System.out.println("You win! :D");
                        gameResult.setText("You Win! :D");
                        gameEnded = true;
                        myturn = false;
    //                    reportGameEnding();
                    }
                }
                if (numOfMoves >= 9){
                    System.out.println("It's a draw!");
                    gameResult.setText("It's a Draw! ");
                    gameEnded = true;
                    myturn = false;
                }
            }            
        }
    }

    public void setControllerStreams(DataInputStream dis, PrintStream ps){
        controllerDIS = dis;
        controllerPS = ps;
    }
    void reportGameEnding(){
        Player player = new Player(myUserName);
        XOInterface xoMsg = new XOInterface(Messages.SINGLE_MODE_FINISHED, player);
        controllerPS.println(new Gson().toJson(xoMsg));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        myturn = SignInController.myTurn;
    }


    @FXML
    private void restart(ActionEvent event) {
        init();
        clearAll();
    }
    void clearAll ()
    {
        pos1.setText("");
        pos2.setText("");
        pos3.setText("");
        pos4.setText("");
        pos5.setText("");
        pos6.setText("");
        pos7.setText("");
        pos8.setText("");
        pos9.setText("");
        gameResult.setText("");
    }
    void sendMyMove(){
        Gamelog gamelog = new Gamelog(gameID, myUserName, opponentUserName);
        XOInterface xoMsg = new XOInterface(Messages.PLAY_MOVE, gamelog, playerPos, playerSymbol);
        Gson g = new Gson();
        controllerPS.println(g.toJson(xoMsg));
    }
    
    public void setIDs(int gameID, String myUserName, String opponentUserName){
        this.gameID = gameID;
        this.myUserName = myUserName;
        this.opponentUserName = opponentUserName;
        homeNameLabel.setText(myUserName);
        opponentNameLabel.setText(opponentUserName);
    }
    
    public void printOpponentMove(int playerPos,boolean _myturn){
        Platform.runLater(()->{
            displayMove(playerPos, opponentSymbol);
            myturn = _myturn;
        });
        
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

    @FXML
    private void sendMessage(ActionEvent event) {
        String chatingMessage = "["+ SignInController.username +"]: "+ textAreaMessanger.getText();
        textAreaMessanger.setText("");
        textScreenMessanger.appendText(chatingMessage+"\n");
        Gamelog onlineGameChating = new Gamelog(myUserName, opponentUserName, chatingMessage);
        XOInterface xointerface = new XOInterface(Messages.Chat_between_GamePlayer, onlineGameChating);
        Gson g = new Gson();
        String message = g.toJson(xointerface);
        System.out.println(message);
        controllerPS.println(message); 
    }

    @FXML
    private void resume(ActionEvent event) {
        clearAll();
        Gamelog gamelog = new Gamelog(gameID, myUserName, opponentUserName);
        XOInterface xoMsg = new XOInterface(Messages.RESUME, gamelog);
        Gson g = new Gson();
        controllerPS.println(g.toJson(xoMsg));
    }
    
    public  void displayMovesOnBoard (char[] savedGame)
    {
        char s= ' ';
        for(int i=0;i<9;i++)
        {
            if(Character.toString(savedGame[i]).equals("-")){
            savedGame[i]=s ; 
            }
            
        }
        pos1.setText(Character.toString(savedGame[0]));
        pos2.setText(Character.toString(savedGame[1]));
        pos3.setText(Character.toString(savedGame[2]));
        pos4.setText(Character.toString(savedGame[3]));
        pos5.setText(Character.toString(savedGame[4]));
        pos6.setText(Character.toString(savedGame[5]));
        pos7.setText(Character.toString(savedGame[6]));
        pos8.setText(Character.toString(savedGame[7]));
        pos9.setText(Character.toString(savedGame[8]));  
    }
       
    public void printMessage(XOInterface xo)
    {
        textScreenMessanger.appendText(xo.getGameLog().getMessage()+"\n");
        System.out.println(textScreenMessanger.getText());
    }       
}