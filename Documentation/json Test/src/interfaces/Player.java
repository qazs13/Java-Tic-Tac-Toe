package interfaces;


public class Player {
    private String userName,passwd,fName,lName;
    private int score,gameId;
    private boolean isPlaying,status;
    public Player(){};
    public Player(String _userName,String _passwd,String _fName,String _lName)
    {
        userName=_userName;
        passwd=_passwd;
        fName=_fName;
        lName=_lName;
    }
    public Player (String userName, boolean status,int score)
    {
        this.userName = userName;
        this.status = status;
        this.score = score;
    }
    public Player (String userName,String fName,String lName,boolean status,int score,boolean isPlaying,int gameId)
    {
        this.userName = userName;
        this.fName = fName;
        this.lName = lName;
        this.status = status;
        this.score = score;
        this.isPlaying = isPlaying;
        this.gameId = gameId;
    }
     public void setUserName(String _userName){ 
            userName=_userName;
    };
    public void setPasswd(String _passwd){
            passwd=_passwd;
    };
    public void setFName(String _fName){
           fName=_fName;
    };
    public void setLName(String _lName){
           lName=_lName;
    };
    public void setStatus(boolean status){
           this.status = status;
    };
    public void setScore(int _score){
           score=_score;
    };
    public void setIsPlaying(boolean _isPlaying){
           isPlaying=_isPlaying;
    };
    public void setGameId(int _gameId){
           gameId=_gameId;
    };
    public String getUserName(){ 
        return userName;
    };
    public String getPasswd(){
    return passwd;
    };
    public String getFName(){
    return fName;
    };
    public String getLName(){
    return lName;
    };
    public boolean getStatus(){
    return status;
    };
    public int getScore(){
    return score;
    };
    public boolean getIsPlaying(){
    return isPlaying;
    };
    public int getGameId(){
    return gameId;
    };    
}
