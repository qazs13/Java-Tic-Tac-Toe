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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import signin.SignInController;
import tictactoegui.TicTacToeGui;


public class MultiPlayerController implements Initializable 
{
    boolean gameEnded;
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
    private Button pos1;;
    @FXML
    private Button pos7;
    @FXML
    private Button pos8;
    private Label userNameLabel;
    @FXML
    private Label gameResult;

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
    @FXML
    private Label homeNameLabel1;
    @FXML
    private Label homeNameLabel11;
    @FXML
    private Button resume;

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
            gameResult.setText("Your Turn");
        }
        else
        {
            playerSymbol = 'O';
            opponentSymbol = 'X';
            gameResult.setText("Their Turn");
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
                    gameResult.setText("Their Turn");
                    if(isWinningPosition(playerMoves)){
                        System.out.println("You win! :D");
                        gameResult.setText("You Win! :D");
                        gameEnded = true;
                        myturn = false;
                        reportGameEnding(true);
                    }
                }
                if (!gameEnded && numOfMoves >= 9){
                    System.out.println("It's a draw!");
                    gameResult.setText("It's a Draw! ");
                    gameEnded = true;
                    myturn = false;
                    reportGameEnding(false);                    
                }
            }            
        }
    }

    void reportGameEnding(boolean state){
        XOInterface xoMsgs = new XOInterface(Messages.GAME_ENDED, new Player(myUserName), new Gamelog(gameID, myUserName, opponentUserName));
        xoMsgs.setOpearationResult(state);
        Gson g = new Gson();
        String messageend = g.toJson(xoMsgs);
        System.out.println(messageend);
        controllerPS.println(messageend);
    }
    public void recieveGameEnding(){
        gameEnded = true;
        myturn = false;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerPS = TicTacToeGui.ps;
        init();
        myturn = SignInController.myTurn;
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
        System.out.println(g.toJson(xoMsg));
        controllerPS.println(g.toJson(xoMsg));
    }
    
    public void setIDs(int gameID, String myUserName, String opponentUserName){
        this.gameID = gameID;
        this.myUserName = myUserName;
        this.opponentUserName = opponentUserName;
        if(myturn){
            Platform.runLater(() -> {
                homeNameLabel.setText(myUserName);
                opponentNameLabel.setText(opponentUserName);
                gameResult.setText("Your Turn");
            });
        }
        else{
            Platform.runLater(() -> {
                homeNameLabel.setText(opponentUserName);
                opponentNameLabel.setText(myUserName);
                gameResult.setText("Their Turn");
            });
        }


    }

    public void printOpponentMove(Integer playerPos,boolean _myturn){
        if (!movesPool.isEmpty() && movesPool.contains(playerPos)) {
            opponentMoves.add(playerPos);
            movesPool.remove(playerPos);
            numOfMoves++;
            if(!gameEnded){
                displayMove(playerPos, opponentSymbol);
                myturn = _myturn;
                if (myturn)
                {
                    gameResult.setText("Your Turn");
                }
                else
                {
                    gameResult.setText("Their Turn");
                }
            }
        }
        if(isWinningPosition(opponentMoves)){
            System.out.println("You Lose! :(");
            gameResult.setText("You Lose! :(");
            gameEnded = true;
            myturn = false;
        }
        if (!gameEnded && numOfMoves >= 9){
            System.out.println("It's a draw!");
            gameResult.setText("It's a Draw! ");
            gameEnded = true;
            myturn = false;
        }

    }
    @FXML
    private void back(ActionEvent event) {

        XOInterface xoMsg = new XOInterface(Messages.BACK,new Player(myUserName));
        Gson g = new Gson();
        controllerPS.println(g.toJson(xoMsg));
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
        Gamelog gamelog = new Gamelog(gameID, myUserName, opponentUserName);
        XOInterface xoMsg = new XOInterface(Messages.RESUME, gamelog);
        Gson g = new Gson();
        controllerPS.println(g.toJson(xoMsg));
    }
    
    public void displayMovesOnBoard (char[] savedGame, String homePlayer, int gameID)
    {
        clearAll();
        init();
        int countHome = 0;
        int countOpponent = 0;
        this.gameID = gameID;
        if(myUserName.equals(homePlayer))
        {
            playerSymbol = 'X';
            homePlayerSign.setText(Character.toString(playerSymbol));
            opponentSymbol = 'O';
            opponenPlayerSign.setText(Character.toString(opponentSymbol));
        }
        else
        {
            playerSymbol = 'O';
            homePlayerSign.setText(Character.toString(playerSymbol));            
            opponentSymbol = 'X';
            opponenPlayerSign.setText(Character.toString(opponentSymbol));
        }
        char s = ' ';
        for(int i=0;i<9;i++)
        {
            Integer move = i+1;
            if(savedGame[i] == '-' )
            {
                savedGame[i] = s ; 
            }
            else if(savedGame[i] == playerSymbol){
                playerMoves.add(move);
                movesPool.remove(move);
                countHome++;
            }
            else{
                opponentMoves.add(move);
                movesPool.remove(move);
                countOpponent++;
            }
        }
        
        if(countHome < countOpponent)
        {
            myturn = true;
            gameResult.setText("Your Turn");            
        }
        else
        {
            myturn = false;
            gameResult.setText("Their Turn");              
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
    
    public void cancelOrEnableResume(boolean state)
    {
        resume.setDisable(state);
    }
}