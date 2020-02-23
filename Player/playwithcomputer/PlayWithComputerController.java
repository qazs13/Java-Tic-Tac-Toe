package playwithcomputer;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import com.google.gson.Gson;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import levelSelection.LevelSelectionController;
import playwithcomputer.GFG.Move;
import signin.SignInController;
import tictactoegui.TicTacToeGui;

/**
 *
 * @author E.S
 */
public class PlayWithComputerController implements Initializable {
    
    private Label label;
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

    PrintStream controllerPS;
    String myUserName = SignInController.username;
    char playerSymbol, AISymbol;
    Vector<Integer> playerMoves= new Vector<>();
    Vector<Integer> AIMoves= new Vector<>();
    Vector<Integer> movesPool= new Vector<>();
    int numOfMoves;
    char [][] board =              {{ '_', '_', '_' }, 
					{ '_', '_', '_' }, 
					{ '_', '_', '_' }};
    int getAIMove(){
        int intMove=0;
        Move gridMove=GFG.findBestMove(board);
        intMove=gridToInt(gridMove);
        return intMove;
    }
    int gridToInt(Move gridMove){
        int outInt=0;
        if(gridMove.row==0 && gridMove.col==0)
            outInt=1;
        else if(gridMove.row==0 && gridMove.col==1)
            outInt=2;
        else if(gridMove.row==0 && gridMove.col==2)
            outInt=3;  
        else if(gridMove.row==1 && gridMove.col==0)
            outInt=4;
        else if(gridMove.row==1 && gridMove.col==1)
            outInt=5; 
        else if(gridMove.row==1 && gridMove.col==2)
            outInt=6;  
        else if(gridMove.row==2 && gridMove.col==0)
            outInt=7; 
        else if(gridMove.row==2 && gridMove.col==1)
            outInt=8;
        else if(gridMove.row==2 && gridMove.col==2)
            outInt=9;        
        return outInt;
    }
    void regMove(int position, char symbol){
        switch (position){
            case 1:
                board[0][0]=symbol;
                break;
            case 2:
                board[0][1]=symbol;
                break;
            case 3:
                board[0][2]=symbol;
                break;
            case 4:
                board[1][0]=symbol;
                break;
            case 5:
                board[1][1]=symbol;
                break;
            case 6:
                board[1][2]=symbol;
                break;
            case 7:
                board[2][0]=symbol;
                break;
            case 8:
                board[2][1]=symbol;
                break;
            case 9:
                board[2][2]=symbol;
                break;
            default:
                break;
        }
        movesPool.remove((Integer) position);
    }    
    @FXML
    private Label userNameLabel;
    @FXML
    private Label gameResult;
    @FXML
    private Text playerSign;
    @FXML
    private Text computerSign;
  
    char getRndSymbol(){
        Random r = new Random();
        String symbols = "XO";
        return symbols.charAt(r.nextInt(symbols.length()));
    }
    boolean gameEnded;
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
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                board[i][j]='_';
        playerMoves.clear();
        AIMoves.clear();
        movesPool.clear();
        playerSymbol = getRndSymbol();
        if (playerSymbol == 'X'){
            AISymbol = 'O';
        }
        else{
            AISymbol = 'X';
        }
        playerSign.setText(Character.toString(playerSymbol));
        computerSign.setText(Character.toString(AISymbol));
        for(int i=0; i<9; i++)
            movesPool.add(i+1);
        numOfMoves = 0;
        gameEnded = false;
    }
    
    Integer getRndMove() {
        int number = (int) (Math.random() * movesPool.size());
        return movesPool.get(number);
    }
    
    void displayMove(Integer position, char symbol){
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
        if (!gameEnded) {
            // Player move
            Integer playerPos = Integer.parseInt(((Control) event.getSource()).getId());
            if (!movesPool.isEmpty() && movesPool.contains(playerPos)) {
                displayMove(playerPos, playerSymbol);
                movesPool.remove(playerPos);
                regMove(playerPos, playerSymbol);                
                playerMoves.add(playerPos);
                numOfMoves++;
                if(isWinningPosition(playerMoves)){
                    System.out.println("You win! :D");
                    gameResult.setText("You Win! :D");
                    gameEnded = true;
                    reportGameEnding();
                }
                                // AI move

                if (!movesPool.isEmpty() && !gameEnded) {
                    Integer AIPos=null;
                    if(LevelSelectionController.gameLevel==0){
                        AIPos = getRndMove();
                    }
                    else if(LevelSelectionController.gameLevel==1){
                        AIPos = getAIMove();
                        regMove(AIPos, AISymbol);                    
                    }

                    displayMove(AIPos, AISymbol);
                    movesPool.remove(AIPos);
                    AIMoves.add(AIPos);
                    numOfMoves++;
                    if(isWinningPosition(AIMoves)){
                        System.out.println("You Lose! :(");
                        gameResult.setText("You Lose! :(");
                        gameEnded = true;
                    }
                }
                if (numOfMoves >= 9 && !gameEnded){
                    System.out.println("It's a draw!");
                    gameResult.setText("It's a Draw! ");
                    gameEnded = true;
                }
            }

        }
    }

    void reportGameEnding(){
        Player player = new Player(myUserName);
        XOInterface xoMsg = new XOInterface(Messages.SINGLE_MODE_FINISHED, player);
        controllerPS.println(new Gson().toJson(xoMsg));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerPS = TicTacToeGui.ps;
        init();
        userNameLabel.setText(myUserName);
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
    
    @FXML
    private void minimize(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void exit(ActionEvent event) {
        Player player=new Player();
        player.setUserName(SignInController.username);
        XOInterface xointerface =new XOInterface (Messages.LOGOUT,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        controllerPS.println(s);        
        Platform.exit();
    }      
}
