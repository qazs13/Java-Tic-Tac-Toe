package interfaces;

import java.util.Vector;


public class XOInterface {
    
    String typeOfOperation = null;
    Player player = null;
    Gamelog gamelog = null;
    public Vector<Player> Players= null;
    int fieldNumber; 
    String homePlayer;
    char signPlayed;
            
    public XOInterface()
    {
        Players = new Vector<>();
    }
    
    public XOInterface(String typeOfOperation,Player player)
    {
        this.typeOfOperation = typeOfOperation;        
        this.player = player;
    }
    public XOInterface(String typeOfOperation,Gamelog gamelog)
    {
        this.typeOfOperation = typeOfOperation;        
        this.gamelog = gamelog;
    }
    public XOInterface(String typeOfOperation,Player player,Gamelog gamlog)
    {
        this.typeOfOperation = typeOfOperation;        
        this.player = player;
        this.gamelog = gamelog;
    }
    
    public String getTypeOfOpearation()
    {
        return typeOfOperation;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    public Gamelog getGameLog()
    {
        return gamelog;
    }    
    
    public void setFieldNumber (int fieldNumber)
    {
        this.fieldNumber = fieldNumber;
    }
    public void setSignPlayed (char signPlayed)
    {
        this.signPlayed = signPlayed;
    }    
    
    public int getFieldNumber ()
    {
        return fieldNumber;
    }
    public char getSignPlayed ()
    {
        return signPlayed;
    }        
}
