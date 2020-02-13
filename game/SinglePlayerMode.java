import java.util.*;

public class SinglePlayerMode {
    char [][] grid = {
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '+', '-', '+', '-'},
            {' ', '|', ' ', '|', ' '}
    };
    char playerSymbol, AISymbol;
    Vector<Integer> playerMoves= new Vector<>();
    Vector<Integer> AIMoves= new Vector<>();
    Vector<Integer> movesPool= new Vector<>();
    char getRndSymbol(){
        Random r = new Random();
        String symbols = "XO";
        return symbols.charAt(r.nextInt(symbols.length()));
    }
    int getRndMove(Vector<Integer> movesPool) {
        int randomElement;
        if(!movesPool.isEmpty()){
            int number = (int) (Math.random() * movesPool.size());
            randomElement = movesPool.get(number);
        }
        else
            randomElement = AIMoves.get(0);
        return randomElement;
    }
    void printGrid(){
        for(int i=0; i<5; i++) {
            for (int j=0; j<5; j++)
                System.out.print(grid[i][j]);
            System.out.println();
        }
    }
    void regMove(int position, char symbol){
        switch (position){
            case 1:
                grid[0][0] = symbol;
                break;
            case 2:
                grid[0][2] = symbol;
                break;
            case 3:
                grid[0][4] = symbol;
                break;
            case 4:
                grid[2][0] = symbol;
                break;
            case 5:
                grid[2][2] = symbol;
                break;
            case 6:
                grid[2][4] = symbol;
                break;
            case 7:
                grid[4][0] = symbol;
                break;
            case 8:
                grid[4][2] = symbol;
                break;
            case 9:
                grid[4][4] = symbol;
                break;
            default:
                break;
        }
        movesPool.remove((Integer) position);
    }
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
    public void run(){
        playerSymbol = getRndSymbol();
        System.out.println("You play with " + playerSymbol);
        if (playerSymbol == 'X')
            AISymbol = 'O';
        else
            AISymbol = 'X';
        for(int i=0; i<9; i++)
            movesPool.add(i+1);
        int numOfMoves = 0;
        while (true){
            System.out.println("Enter a position from 1 to 9");
            printGrid();
            // Player move
            Scanner input = new Scanner(System.in);
            Integer playerPos = input.nextInt();
            if(movesPool.contains(playerPos)) {
                regMove(playerPos, playerSymbol);
                playerMoves.add(playerPos);
                if(isWinningPosition(playerMoves)){
                    printGrid();
                    System.out.println("You win!");
                    break;
                }
                numOfMoves++;

                // AI move
                int AIPos = getRndMove(movesPool);
                regMove(AIPos, AISymbol);
                AIMoves.add(AIPos);
                if(isWinningPosition(AIMoves)){
                    printGrid();
                    System.out.println("AI wins :(");
                    break;
                }
                numOfMoves++;
            }
            else
                System.out.println("Not valid!");
            if(numOfMoves >= 9) {
                System.out.println("Its a draw!");
                break;
            }
        }
    }

    public static void main(String [] args){
        SinglePlayerMode game = new SinglePlayerMode();
        game.run();
    }
}
