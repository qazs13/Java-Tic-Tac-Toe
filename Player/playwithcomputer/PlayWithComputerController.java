/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playwithcomputer;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import signin.SignInController;

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

    DataInputStream controllerDIS;
    PrintStream controllerPS;
    String myUserName = SignInController.username;
    char playerSymbol, AISymbol;
    Vector<Integer> playerMoves= new Vector<>();
    Vector<Integer> AIMoves= new Vector<>();
    Vector<Integer> movesPool= new Vector<>();
    int numOfMoves;
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
                playerMoves.add(playerPos);
                numOfMoves++;
                if(isWinningPosition(playerMoves)){
                    System.out.println("You win! :D");
                    gameResult.setText("You Win! :D");
                    gameEnded = true;
                    reportGameEnding();
                }
            }
            // AI move
            if (!movesPool.isEmpty() && !gameEnded) {
                Integer AIPos = getRndMove();
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
            if (numOfMoves >= 9){
                System.out.println("It's a draw!");
                gameResult.setText("It's a Draw! ");
                gameEnded = true;
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
        userNameLabel.setText(myUserName);
    }

    @FXML
    private void back(ActionEvent event) {
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
}