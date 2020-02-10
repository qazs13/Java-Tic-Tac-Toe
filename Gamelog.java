
import java.security.Timestamp;


public class Gamelog {
    int gameId;
    String homePlayer,opponentPlayer;
    Timestamp startGameDate,endGameDate;
    boolean isFinished;
    int size=9;
    Character[] savedGame=new Character[size];
    public Gamelog(){};
    public Gamelog(int _gameId,String _homePlayer,String _opponentPlayer,Timestamp _startGameDate,Timestamp _endGameDate,boolean _isFinished){
        gameId=_gameId;
        homePlayer=_homePlayer;
        opponentPlayer=_opponentPlayer;
        startGameDate=_startGameDate;
        endGameDate=_endGameDate;
        isFinished=_isFinished; 
    };
    public void setGameId(int _gameId){ 
            gameId=_gameId;
    };
    public void setHomePlayer(String _homePlayer){
            homePlayer=_homePlayer;
    };
    public void setOpponentPlayer(String _opponentPlayer){
           opponentPlayer=_opponentPlayer;
    };
    public void setStartGameDate(Timestamp _startGameDate){
           startGameDate=_startGameDate;
    };
    public void setEndGameDate(Timestamp _endGameDate){
           endGameDate=_endGameDate;
    };
    public void setIsFinished(boolean _isFinished){
           isFinished=_isFinished;
    };
    public void setSavedGame(Character [] _savedGame){
           for(int i=0;i<size;i++){
               savedGame[i]=_savedGame[i];
           }

    };
    public int getGameId(){ 
        return gameId;
    };
    public String getHomePlayer(){
    return homePlayer;
    };
    public String getOpponentPlayer(){
    return opponentPlayer;
    };
    public Timestamp getStartGameDate(){
    return startGameDate;
    };
    public Timestamp getEndGameDate(){
    return endGameDate;
    };
    public boolean getIsFinished(){
    return isFinished;
    };
}
