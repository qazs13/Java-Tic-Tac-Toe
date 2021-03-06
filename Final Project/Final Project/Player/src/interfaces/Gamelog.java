package interfaces;

import java.security.Timestamp;

public class Gamelog {
    int gameId;
    String homePlayer,opponentPlayer,message;
    Timestamp startGameDate,endGameDate;
    boolean isFinished;
    char[] savedGame = new char[9];
    public Gamelog(){};
    public Gamelog(String homePlayer, String opponentPlayer)
    {
        this.homePlayer = homePlayer;
        this.opponentPlayer = opponentPlayer;
    }
    
    public Gamelog (int gameId)
    {
        this.gameId = gameId;
    }
    
    public Gamelog(int gameId ,String homePlayer,String opponentPlayer){
        this.gameId = gameId;
        this.homePlayer= homePlayer;
        this.opponentPlayer= opponentPlayer;
    }
    
    public Gamelog(String homePlayer, String opponentPlayer,String message)
    {
        this.homePlayer = homePlayer;
        this.opponentPlayer = opponentPlayer;
        this.message = message;
    }    
    
    public Gamelog(int gameId ,String _homePlayer,String _opponentPlayer,Timestamp _startGameDate,Timestamp _endGameDate,boolean _isFinished,char[] savedGame){
        this.gameId = gameId;
        this.homePlayer=_homePlayer;
        this.opponentPlayer=_opponentPlayer;
        this.startGameDate=_startGameDate;
        this.endGameDate=_endGameDate;
        this.isFinished=_isFinished; 
        this.savedGame = savedGame;
    }
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
    public void setSavedGame(char[] _savedGame){
           savedGame = _savedGame;
    };
    public void setMessage(String _message)
    {
        message = _message;
    }
    public String getMessage()
    {
        return message;
    }    
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
    public char[] getSavedGame()
    {
        return savedGame;
    }
}
