package database;


import java.sql.*;
import interfaces.*;

public class Database {
    
    private final String url = "jdbc:postgresql://localhost/javagame";
    private final String user = "postgres";
    private final String password = "amrwsk13";

    private Connection connection = null;
    private PreparedStatement preparedStatment = null;
    private ResultSet result = null;
    private String sqlCommand;
    
    private Player player = null;
    private Gamelog gameLog = null;
    private XOInterface xoInterface = null;
    boolean state = false; //Momken Nshylo 3ady
    int gameNumber = 0; //Momken Nshylo 3ady
    char[] ch = null;
    
    public Connection connect ()
    {
        try
        {
            connection = DriverManager.getConnection(url,user,password);
            System.err.println("Connection is made successfully");
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            return connection;
        }
    }
    
    public boolean createplayer(XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT createplayer(?,?,?,?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            preparedStatment.setString(2, xoPlayer.getPlayer().getPasswd());
            preparedStatment.setString(3, xoPlayer.getPlayer().getFName());
            preparedStatment.setString(4, xoPlayer.getPlayer().getLName());
            preparedStatment.executeQuery();       
            System.out.println("New player is created");
            close();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean loginCheck (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT logincheck(?,?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            preparedStatment.setString(2, xoPlayer.getPlayer().getPasswd());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                state = result.getBoolean(1);
                System.out.println(result.getBoolean(1));
            }
            System.out.println("Login checked has done to user "+user+" and password "+password);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return state;
        }
    }
    
    
    public XOInterface retriveAllPlayers ()
    {
                    
        try
        {
            connect();
            xoInterface = new XOInterface();
            sqlCommand = "SELECT * FROM retrieveplayers()";
            preparedStatment = connection.prepareStatement(sqlCommand);
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                xoInterface.Players.add(new Player(result.getString(1),result.getBoolean(2),result.getInt(3)));
            }
            System.out.println("Data has been retrived");            
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return xoInterface;
        }
    }
    
    
    public boolean checkPlayerStatus (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT * FROM isPlayerOnline(?)";            
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();

            while (result.next())
            {
                System.out.println(result.getBoolean(1));
                state = result.getBoolean(1);
            }
            System.out.println("Player status is checked");            
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return state;
        }
    }
    
    public boolean updateScoreOffline (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT * FROM updatescoreoffline(?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();
            System.out.println("Score is Updated After winning an offline game");
            close();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateScoreOnline (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT * FROM updatescoreonline(?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();
            System.out.println("Score is Updated After winning an online game");
            close();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean isPlaying (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT * FROM isPlaying(?)";            
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();

            while (result.next())
            {
                System.err.println(result.getBoolean(1));
                state = result.getBoolean(1);
            }
            System.out.println("Is "+ xoPlayer.getPlayer().getUserName() +" playing (" + state+")");            
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return state;
        }        
    }
   
    
    public int createGame (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT createagame(?,?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getGameLog().getHomePlayer());
            preparedStatment.setString(2, xoPlayer.getGameLog().getOpponentPlayer());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                gameNumber = result.getInt(1);
            }
            
            System.out.println("Game is created between " + xoPlayer.getGameLog().getHomePlayer() + 
                    " and " + 
                    xoPlayer.getGameLog().getOpponentPlayer() +
                    "and game number is " + gameNumber);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return gameNumber;
        }
    }
    
    public boolean endGame (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT endgame(?)";
            preparedStatment = connection.prepareCall(sqlCommand);
            preparedStatment.setInt(1, xoPlayer.getGameLog().getGameId());
            preparedStatment.executeQuery();
            System.out.println("Game number " + xoPlayer.getGameLog().getGameId() + " is ended");
            close();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    public boolean setGameMove (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT setmove(?,?,?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setInt(1, xoPlayer.getFieldNumber());
            preparedStatment.setInt(2, (int) xoPlayer.getSignPlayed());
            preparedStatment.setInt(3, xoPlayer.gamelog.getGameId());
            preparedStatment.executeQuery();
            System.out.println("Move is saved to the database");
            close();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public char[] getSavedGame (XOInterface xoPlayer)        //Hayt8yar be user
    {
        try
        {
            ch = new char[9];
            connect();
            sqlCommand = "SELECT * FROM getsaveddata(?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setInt(1, xoPlayer.gamelog.getGameId());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                ch[0] = (char) result.getInt(1);
                ch[1] = (char) result.getInt(2);
                ch[2] = (char) result.getInt(3);
                ch[3] = (char) result.getInt(4);
                ch[4] = (char) result.getInt(5);
                ch[5] = (char) result.getInt(6);
                ch[6] = (char) result.getInt(7);
                ch[7] = (char) result.getInt(8);
                ch[8] = (char) result.getInt(9);
            }
            close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
         return ch;
        }
    }
    
    private void close ()
    {
        try
        {
            System.out.println("Database is closed");
            preparedStatment.close();
            connection.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }    
    
    public static void main(String[] args) {
        Database db = new Database();
        XOInterface xoPlayer = new XOInterface("creatPlayer",new Gamelog(2));
        xoPlayer.setFieldNumber(7);
        xoPlayer.setSignPlayed('x');
        if (xoPlayer.getTypeOfOpearation().equals("creatPlayer"))
        {
            db.setGameMove(xoPlayer);
        }
        xoPlayer = new XOInterface("", new Gamelog(2));
        System.out.println(db.getSavedGame(xoPlayer));
    }
}
