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
    char[] ch = null;
    
    private void connect ()
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
    
    public boolean checkLogIn (XOInterface xoPlayer)
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
            }
            System.out.println("Login checked has done to user "+xoPlayer.getPlayer().getUserName()
                    +" and password "+xoPlayer.getPlayer().getPasswd());
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
    
    public boolean checkSignUp (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT checkNewPlayer(?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                state = result.getBoolean(1);
            }
            System.out.println("The User Name "+xoPlayer.getPlayer().getUserName()+" is checked and the result is "+state);
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
    
    public XOInterface makePlayerOnline (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT makeplayeronline(?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                state = result.getBoolean(1);
            }
            xoPlayer.setOpearationResult(state);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return xoPlayer;
        }
    }
    
    public boolean makePlayerIsPlaying (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT setIsPlayerPlaying (?)";
            preparedStatment = connection.prepareStatement(sqlCommand); 
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            preparedStatment.executeQuery();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            return true;
        }
    }
    
    public boolean makeAllPlayersOffline ()
    {
        try
        {
            connect();
            sqlCommand = "SELECT makeAllPlayersOffline ()";
            preparedStatment = connection.prepareStatement(sqlCommand);
            return preparedStatment.execute();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
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
    
    
    public XOInterface retrivePlayerData (XOInterface xoPlayer)
    {
        try
        {
            connect();
            player = new Player();
            sqlCommand = "SELECT * from retriveplayerdata(?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                player.setFName(result.getString(1));
                player.setLName(result.getString(2));
                player.setStatus(result.getBoolean(3));
                player.setScore(result.getInt(4));
            }
            xoInterface = new XOInterface("playerData", player);
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
   
    
    public XOInterface createGame (XOInterface xoPlayer)
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
                xoPlayer.getGameLog().setGameId(result.getInt(1));
            }
            
            System.out.println("Game is created between " + xoPlayer.getGameLog().getHomePlayer() + 
                    " and " + 
                    xoPlayer.getGameLog().getOpponentPlayer() +
                    "and game number is " + xoPlayer.getGameLog().getGameId());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            close();
            return xoPlayer;
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
    
    public XOInterface getScore (XOInterface xoPlayer)
    {
        try
        {
            connect();
            int score = 0;
            sqlCommand = "select returnScorePlayer (?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setString(1, xoPlayer.getPlayer().getUserName());
            result = preparedStatment.executeQuery();
            while (result.next())
            {
                score = result.getInt(1);
            }
            xoPlayer = new XOInterface(" ", new Player(xoPlayer.getPlayer().getUserName(),score));
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            return xoPlayer;
        }
    }
    
    
    public XOInterface setGameMove (XOInterface xoPlayer)
    {
        try
        {
            connect();
            sqlCommand = "SELECT setmove(?,?,?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setInt(1, xoPlayer.getFieldNumber());
            preparedStatment.setInt(2, (int) xoPlayer.getSignPlayed());
            preparedStatment.setInt(3, xoPlayer.getGameLog().getGameId());
            preparedStatment.executeQuery();
            System.out.println("Move is saved to the database");
            close();
            return xoPlayer;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return new XOInterface("gameIsNotSetted");
        }
    }
    
    public XOInterface getSavedGame (XOInterface xoPlayer)
    {
        try
        {
            ch = new char[9];
            connect();
            sqlCommand = "SELECT * FROM getsaveddata(?,?,?)";
            preparedStatment = connection.prepareStatement(sqlCommand);
            preparedStatment.setInt(1, xoPlayer.getGameLog().getGameId());
            preparedStatment.setString(2, xoPlayer.getGameLog().getHomePlayer());
            preparedStatment.setString(3, xoPlayer.getGameLog().getOpponentPlayer());
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
            xoPlayer.getGameLog().setSavedGame(ch);
            close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
         return xoPlayer;
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

}
