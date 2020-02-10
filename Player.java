
public class Player {
//    public static DataBase companyDB = new DataBase();
    String userName,passwd,fName,lName;
    int score,gameId;
    boolean isPlaying,status;
    public Player(){};
    public Player(String _userName,String _passwd,String _fName,String _lName,boolean _status,int _score,boolean _isPlaying,int _gameId)
    {
        userName=_userName;
        passwd=_passwd;
        fName=_fName;
        lName=_lName;
        status=_status;
        score=_score;
        isPlaying=_isPlaying;
        gameId=_gameId; 
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
    public void setStatus(boolean _status){
           status=_status;
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
